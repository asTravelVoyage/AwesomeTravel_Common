package renewal.common.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import renewal.common.entity.AirportCode;
import renewal.common.entity.Location;
import renewal.common.entity.Location.LocationType;
import renewal.common.entity.Product;
import renewal.common.entity.Product.DepartTimeType;
import renewal.common.entity.Product.ProductStatus;
import renewal.common.entity.PurchaseBase.PurchaseStatus;
import renewal.common.entity.PurchaseProduct;
import renewal.common.entity.Schedule;
import renewal.common.entity.SeatClass;
import renewal.common.entity.Tour;
import renewal.common.repository.ProductRepository;
import renewal.common.repository.PurchaseProductRepository;
import renewal.common.repository.SeatClassRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceCommon {

    private final ProductRepository productRepo;
    private final PurchaseProductRepository purchaseProductRepo;
    private final SeatClassRepository seatClassRepo;

    public Product calcSingleProduct(Product product, LocalDate departDate) {
        // System.out.println("\n=== calcSingleProduct 호출 ===");
        // System.out.println("target.id = " + product.getId());
        // System.out.println("target.name = " + product.getTitle());
        // System.out.println("departDate = " + departDate);

        Hibernate.initialize(product.getTour());
        Tour tour = product.getTour();

        // 초기 가격 계산
        Long finalPriceAdult = tour.getPriceAdult();
        Long finalPriceYouth = tour.getPriceYouth();
        Long finalPriceInfant = tour.getPriceInfant();

        List<Schedule> schedules = tour.getSchedules();

        for (Schedule sced : schedules) {
            List<Location> locations = sced.getLocations();
            for (Location loc : locations) {
                LocationType type = loc.getLocationType();
                if (type == LocationType.AIR) {

                    // // cutoffDays 적용 여부 선택
                    // LocalDate departDate = today;
                    // if (applyCutoff) {
                    // departDate = departDate.plusDays(product.getCutoffDays());
                    // }
                    // departDate = departDate.plusDays(sced.getDay());

                    LocalDate currentdepartDate = departDate.plusDays(sced.getDay());
                    DepartTimeType dtt = product.getDepartTimeType();
                    int startHour = sced.getDay() == 0 ? dtt.getStartHour() : 0;
                    int endHour = sced.getDay() == 0 ? dtt.getEndHour() : 23;

                    LocalDateTime startDateTime = currentdepartDate.atTime(startHour, 0);
                    LocalDateTime endDateTime = currentdepartDate.atTime(endHour, 59, 59);

                    AirportCode departAirport = loc.getDepartAirport();
                    AirportCode arriveAirport = loc.getArriveAirport();
                    // System.out.println("\n=======findLowestPriceSeat========");
                    // System.out.println(startDateTime);
                    // System.out.println(endDateTime);
                    // System.out.println(departAirport.getAirportCode());
                    // System.out.println(arriveAirport.getAirportCode());
                    // System.out.println(product.getSeatClassTypes());
                    // System.out.println("=======findLowestPriceSeat========\n");

                    SeatClass finalSeat = seatClassRepo.findLowestPriceSeat(
                            startDateTime,
                            endDateTime,
                            departAirport,
                            arriveAirport,
                            product.getSeatClassTypes());

                    // 항공권 없으면 null 반환
                    if (finalSeat == null) {
                        // System.out.println(
                        // "==================== null 반환: 해당 날짜(" + currentdepartDate
                        // + ")에 항공편 없음=====================");

                        return null;
                    }

                    loc.setSeatClass(finalSeat);

                    if (product.getDepartDateTime() == null) { // 첫 항공권 출발시간 (=출국시간)
                        product.setDepartDateTime(finalSeat.getAir().getDepartDateTime());
                    }

                    // 한 product에 대해 항공권 도착시간 계속 덮어씌움 => 마지막 항공권의 도착시간 (=귀국시간)
                    product.setReturnDateTime(finalSeat.getAir().getArriveDateTime());

                    // 항공권 잔여좌석 확인로직 -> 해당 날짜의 상품 예약자 수 확인로직으로 변경
                    // // 한 product에 대해 항공권 잔여좌석 낮은쪽 계속 덮어씌움 => 예약 가능인 수 저장
                    // if (product.getAvailableSeats() == null
                    // || product.getAvailableSeats() > finalSeat.getAvailableSeats()) {
                    // product.setAvailableSeats(finalSeat.getAvailableSeats());
                    // }

                    finalPriceAdult += finalSeat.getPriceAdult();
                    finalPriceYouth += finalSeat.getPriceYouth();
                    finalPriceInfant += finalSeat.getPriceInfant();

                } else if (type == LocationType.HOTEL) {
                    finalPriceAdult += loc.getHotel().getPrice();
                    finalPriceYouth += loc.getHotel().getPrice();
                    // 영유아는 호텔 포함 안함
                }
            }
        }

        product.setFinalPriceAdult(finalPriceAdult);
        product.setFinalPriceYouth(finalPriceYouth);
        product.setFinalPriceInfant(finalPriceInfant);

        // 항공권 잔여좌석 확인로직 -> 해당 날짜의 상품 예약자 수 확인로직으로 변경
        Long reserved = 0L;
        // 해당 날짜의 상품 예약들
        List<PurchaseProduct> purchaseProducts = purchaseProductRepo.findByProductAndDepartDate(product, departDate);
        for (PurchaseProduct pp : purchaseProducts) {
            if (pp.getPurchaseStatus() != PurchaseStatus.CANCELLED && !pp.isWaiting()) {
                reserved += pp.getAdultCount();
                reserved += pp.getYouthCount();
                // reserved += pp.getInfantCount(); // 영유아는 인원수 카운트 안함
            }

            // waiting인 주문이 하나라도 있으면 예약대기 상품임
            if (pp.isWaiting()) {
                product.setProductStatus(ProductStatus.WAITING);
            }
        }
        product.setReservedSeats(reserved);
        product.setAvailableSeats(product.getTour().getMaxCapacity() - reserved);

        return product;
    }

    ResponseEntity<?> cancelPurchase(
            @PathVariable Long id,
            @RequestBody Map<String, Object> dummyload,
            Principal principal,
            Model model) {

        PurchaseProduct purchaseProduct = purchaseProductRepo.findById(id).get();
        purchaseProduct.setPurchaseStatus(PurchaseStatus.CANCELLED);
        purchaseProduct.setWaiting(false);
        purchaseProductRepo.save(purchaseProduct);

        List<PurchaseProduct> candidate = purchaseProductRepo
                .findByProductAndDepartDate(
                        purchaseProduct.getProduct(),
                        purchaseProduct.getDepartDateTime().toLocalDate());

        Product product = productRepo.findById(purchaseProduct.getProduct().getId()).get();
        Product calcedProduct = calcSingleProduct(product, purchaseProduct.getDepartDateTime().toLocalDate());

        for (PurchaseProduct candidatePP : candidate) {
            Long totalRequiredSeats = candidatePP.getAdultCount() + candidatePP.getYouthCount();
            if (candidatePP.isWaiting() && candidatePP.getPurchaseStatus() != PurchaseStatus.CANCELLED) {
                if (totalRequiredSeats <= calcedProduct.getAvailableSeats()) {

                    calcedProduct.setAvailableSeats(calcedProduct.getAvailableSeats() + totalRequiredSeats);
                    calcedProduct.UpdateProductStatus();
                    productRepo.save(calcedProduct);

                    candidatePP.setWaiting(false);
                    purchaseProductRepo.save(candidatePP);
                    // TODO candidatePP 해당 사용자 알람 보내기
                } else {
                    break; // 순번 건너뛰기 방지
                }
            }
        }

        return ResponseEntity.ok().build();
    }
}
