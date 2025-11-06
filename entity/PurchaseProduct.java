package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProduct extends PurchaseBase {

    @ManyToOne
    @JoinColumn
    private Product product; // 구매상품

    // @OneToMany
    // private List<Passenger> passengers = new ArrayList<>();

}
