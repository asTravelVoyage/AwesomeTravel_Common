package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long purchaseId; // 구매 ID (AirPurchase 또는 PurchaseProduct)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundType refundType; // 환불 유형 (항공/패키지)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status; // 환불 상태

    @Column(nullable = false)
    private Long amount; // 환불 금액

    @Column(columnDefinition = "TEXT")
    private String reason; // 환불 사유

    @Column(nullable = false)
    private LocalDateTime requestDate; // 환불 요청일

    @Column
    private LocalDateTime processedDate; // 환불 처리일

    @Column
    private String adminNote; // 관리자 메모

    // 항공권 환불 생성자
    public Refund(PurchaseAir airPurchase, Long amount, String reason) {
        this.purchaseId = airPurchase.getId();
        this.refundType = RefundType.AIR;
        this.status = RefundStatus.REQUESTED;
        this.amount = amount;
        this.reason = reason;
        this.requestDate = LocalDateTime.now();
    }

    // 패키지 환불 생성자
    public Refund(PurchaseProduct productPurchase, Long amount, String reason) {
        this.purchaseId = productPurchase.getId();
        this.refundType = RefundType.PRODUCT;
        this.status = RefundStatus.REQUESTED;
        this.amount = amount;
        this.reason = reason;
        this.requestDate = LocalDateTime.now();
    }

    // 환불 승인
    public void approve(String adminNote) {
        this.status = RefundStatus.APPROVED;
        this.processedDate = LocalDateTime.now();
        this.adminNote = adminNote;
    }

    // 환불 거절
    public void reject(String adminNote) {
        this.status = RefundStatus.REJECTED;
        this.processedDate = LocalDateTime.now();
        this.adminNote = adminNote;
    }

    // 환불 완료
    public void complete() {
        this.status = RefundStatus.COMPLETED;
        this.processedDate = LocalDateTime.now();
    }

    public enum RefundStatus {
        REQUESTED, // 환불 요청
        APPROVED, // 환불 승인
        REJECTED, // 환불 거절
        COMPLETED // 환불 완료
    }

    public enum RefundType {
        AIR, // 항공권
        PRODUCT // 패키지
    }
}
