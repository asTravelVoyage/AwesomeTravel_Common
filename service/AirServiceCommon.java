package renewal.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import renewal.common.entity.PurchaseAir;
import renewal.common.entity.PurchaseBase.ConfirmedSeatClass;
import renewal.common.entity.PurchaseBase.PurchaseStatus;
import renewal.common.entity.SeatClass;
import renewal.common.repository.PurchaseAirRepository;
import renewal.common.repository.SeatClassRepository;

@Service
@RequiredArgsConstructor
public class AirServiceCommon {

    private final PurchaseAirRepository purchaseAirRepo;
    private final SeatClassRepository seatClassRepo;

@Transactional
public void cancelPurchase(Long id) {

        PurchaseAir purchaseAir = purchaseAirRepo.findById(id).orElseThrow();
        purchaseAir.setPurchaseStatus(PurchaseStatus.CANCELLED);
        purchaseAirRepo.save(purchaseAir);

        List<ConfirmedSeatClass> restoreTarget = purchaseAir.getFinalSeatClasses();

        for (ConfirmedSeatClass confirmedSeatClass : restoreTarget) {

            SeatClass seatClass = seatClassRepo
                    .findByAirIdAndClassType(confirmedSeatClass.getAirId(), confirmedSeatClass.getClassType())
                    .orElseThrow();

            Long adult = confirmedSeatClass.getSeatCountAdult() != null ? confirmedSeatClass.getSeatCountAdult() : 0L;
            Long youth = confirmedSeatClass.getSeatCountYouth() != null ? confirmedSeatClass.getSeatCountYouth() : 0L;

            long restoreCount = adult + youth;

            seatClass.setAvailableSeats(seatClass.getAvailableSeats() + restoreCount);
            seatClassRepo.save(seatClass);
        }

        // 완료
    }

}
