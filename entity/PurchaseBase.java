package renewal.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class PurchaseBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected PurchaseStatus purchaseStatus;

    @Column(nullable = false)
    protected Long price; // 결제금액

    @ManyToOne
    @JoinColumn
    protected User user; // 구매자

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

    public PurchaseBase(Long price, User user, String name, String number, String email,
            LocalDateTime purchaseDate, LocalDateTime paymentDueDate) {
        this.price = price;
        this.user = user;
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

    @OneToMany
    private List<Passenger> passengers = new ArrayList<>();
}
