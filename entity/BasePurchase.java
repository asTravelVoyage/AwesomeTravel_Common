package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class BasePurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_purchase_id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected PurchaseStatus purchaseStatus;

    @Column(nullable = false)
    protected Integer price; // 결제금액

    @Column(nullable = false)
    protected Long member_id; // 구매자

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String number;

    @Column(nullable = false)
    protected String email;

    @Column(nullable = false)
    protected LocalDateTime purchaseDate; // 구매일

    @Column
    protected LocalDateTime paymentDueDate; // 결제기한

    public BasePurchase(Integer price, Long member_id, String name, String number, String email,
            LocalDateTime purchaseDate, LocalDateTime paymentDueDate) {
        this.price = price;
        this.member_id = member_id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.purchaseDate = purchaseDate;
        this.paymentDueDate = paymentDueDate;
    }

    public enum PurchaseStatus {
        HOLDING, // 결제 대기 상태
        PAID, // 결제 완료
        CANCELLED // 결제 실패 또는 시간 초과
    }

}
