package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProduct extends PurchaseBase {

    @ManyToOne
    @JoinColumn
    private Product product; //구매상품

    // @OneToMany
    // private List<Passenger> passengers = new ArrayList<>();
    
    private Integer expectedPassengerCount;
    private Boolean isPassengerInfoComplete = false;
    private java.time.LocalDateTime passengerInfoDeadline;
    private Boolean transactionComplete = false;
    
    // Alias getters
    public Long getPurchaseProductId() {
        return this.id;
    }
    
    // public List<Passenger> getPassengerProducts() {
    //     return this.passengers;
    // }
    
    public Long getMember_id() {
        return this.user != null ? this.user.getId() : null;
    }
    
    public void setPassengerInfoDeadline(java.time.LocalDateTime deadline) {
        this.passengerInfoDeadline = deadline;
    }
    
    public void setIsPassengerInfoComplete(Boolean complete) {
        this.isPassengerInfoComplete = complete;
    }
    
    public void setPurchaseStatus(PurchaseStatus newStatus) {
        this.purchaseStatus = newStatus;
    }
    
    public boolean isPassengerInfoComplete() {
        return this.isPassengerInfoComplete != null && this.isPassengerInfoComplete;
    }
    
    public boolean isTransactionComplete() {
        return this.transactionComplete != null && this.transactionComplete;
    }
    
    // Service에서 사용하는 생성자
    public PurchaseProduct(Product product, Long productPurchaseId, Long price, Long memberId,
                          String name, String number, String email,
                          java.time.LocalDateTime purchaseDate, java.time.LocalDateTime paymentDueDate,
                          int expectedPassengerCount) {
        super(price, null, name, number, email, purchaseDate, paymentDueDate);
        this.product = product;
        this.expectedPassengerCount = expectedPassengerCount;
    }

}
