package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user; // 구매자

    @ManyToOne
    @JoinColumn(name = "purchase_product_id")
    private PurchaseProduct purchaseProduct; // 대상

    @ManyToOne
    @JoinColumn(name = "purchase_air_id")
    private PurchaseAir purchaseAir; // 대상

    private PaymentStatus purchaseStatus;

    private Long price; // 결제금액
    private PaymentMethod paymentMethod;

    private LocalDateTime purchaseDate; // 구매일

    public enum PaymentStatus {
        HOLDING, // 결제 대기 상태
        PAID, // 결제 완료
        CANCELLED, // 결제 취소
        REFUND
    }

    public enum PaymentMethod {
        CARD,
        BANK,
        ETC
    }

}
