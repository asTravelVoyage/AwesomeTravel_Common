package renewal.common.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import renewal.awesome_travel.air.dto.AirDetailResponseDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseAir extends PurchaseBase {
    public static PurchaseAir from(AirDetailResponseDto dto, User user) {

        PurchaseAir purchase = new PurchaseAir();

        // 기본 정보
        purchase.title = "항공권 예약 (" + dto.getSeatClasses().size() + "구간)";
        purchase.purchaseStatus = PurchaseStatus.HOLDING;

        // 가격 정보
        purchase.finalPriceAdult = dto.getAdultTotal();
        purchase.finalPriceYouth = dto.getYouthTotal();
        purchase.finalPriceInfant = dto.getInfantTotal();

        purchase.adultCount = (long) dto.getDetailRequest().getAdultCount();
        purchase.youthCount = (long) dto.getDetailRequest().getYouthCount();
        purchase.infantCount = (long) dto.getDetailRequest().getInfantCount();

        purchase.price = dto.getPriceTotal();

        // 구매자
        purchase.user = user;
        purchase.name = user.getName();
        purchase.number = user.getPhone() != null ? user.getPhone() : "01000000000";
        purchase.email = user.getEmail();

        // 구매 시간
        purchase.purchaseDate = LocalDateTime.now();

        purchase.isTransactionComplete = false;
        purchase.isPassengerInfoComplete = false;

        // 결제 기한 및 동승자 정보 기한 (원하면 수정 가능)
        purchase.paymentDueDate = LocalDateTime.now().plusHours(2);
        purchase.passengerInfoDeadline = LocalDateTime.now().plusDays(1);

        // ------- 좌석 정보 매핑 -------
        List<ConfirmedSeatClass> confirmed = dto.getSeatClasses().stream()
                .map(sc -> new ConfirmedSeatClass(
                        sc,
                        purchase.adultCount,
                        purchase.youthCount,
                        purchase.infantCount))
                .toList();

        purchase.setFinalSeatClasses(confirmed);

        return purchase;
    }
}
