package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseAir extends PurchaseBase {

    @ManyToOne
    @JoinColumn
    private SeatClass seatClass;
    
    // @OneToMany
    // private List<Passenger> passengers = new ArrayList<>();
    
    private Integer expectedPassengerCount;
    private Boolean isPassengerInfoComplete = false;
    private LocalDateTime passengerInfoDeadline;
    private Boolean transactionComplete = false;

    public PurchaseAir(SeatClass seatClass, Long price, User user, String name, String number, String email, LocalDateTime purchaseDate, LocalDateTime paymentDueDate) {
        super(price, user, name, number, email, purchaseDate, paymentDueDate);
        this.seatClass = seatClass;
    }
    
    // Service에서 사용하는 생성자 (User 대신 member_id Long 받음)
    public PurchaseAir(SeatClass seatClass, Long productPurchaseId, Long price, Long memberId, 
                      String name, String number, String email, 
                      LocalDateTime purchaseDate, LocalDateTime paymentDueDate, 
                      int expectedPassengerCount) {
        super(price, null, name, number, email, purchaseDate, paymentDueDate);
        this.seatClass = seatClass;
        this.expectedPassengerCount = expectedPassengerCount;
        // productPurchaseId와 memberId는 별도로 처리 (필요시 setter 사용)
    }
    
    // Alias getters for backward compatibility
    public Long getProductPurchaseId() {
        return this.id;
    }
    
    // public List<Passenger> getPassengerAirs() {
    //     return this.passengers;
    // }
    
    public Long getMember_id() {
        return this.user != null ? this.user.getId() : null;
    }
    
    public void setPassengerInfoDeadline(LocalDateTime deadline) {
        this.passengerInfoDeadline = deadline;
    }
    
    public void setIsPassengerInfoComplete(Boolean complete) {
        this.isPassengerInfoComplete = complete;
    }
    
    public Boolean getIsPassengerInfoComplete() {
        return this.isPassengerInfoComplete;
    }
    
    public boolean isPassengerInfoComplete() {
        return this.isPassengerInfoComplete != null && this.isPassengerInfoComplete;
    }
    
    public Boolean getTransactionComplete() {
        return this.transactionComplete;
    }
    
    public boolean isTransactionComplete() {
        return this.transactionComplete != null && this.transactionComplete;
    }

    public void setPurchaseStatus(PurchaseStatus newStatus) {
        // 상태가 이미 PAID인데 다시 HOLDING으로 바꾸려는 경우 방지
        if (this.purchaseStatus == PurchaseStatus.PAID && newStatus == PurchaseStatus.HOLDING) {
            throw new IllegalStateException("결제 완료된 구매는 HOLDING 상태로 되돌릴 수 없습니다.");
        }
        if (this.purchaseStatus == PurchaseStatus.CANCELLED && newStatus != PurchaseStatus.CANCELLED) {
            throw new IllegalStateException("취소된 구매는 상태를 변경할 수 없습니다.");
        }

        this.purchaseStatus = newStatus;
    }
}
