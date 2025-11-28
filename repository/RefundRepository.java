package renewal.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.Refund;
import renewal.common.entity.Refund.RefundStatus;
import renewal.common.entity.Refund.RefundType;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    
    // 구매 ID와 타입으로 환불 조회
    Optional<Refund> findByPurchaseIdAndRefundType(Long purchaseId, RefundType refundType);
    
    // 상태별 환불 목록 조회
    List<Refund> findByStatus(RefundStatus status);
    
    // 구매 ID로 환불 목록 조회
    List<Refund> findByPurchaseId(Long purchaseId);
}

