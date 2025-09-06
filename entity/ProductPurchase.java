package renewal.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchase extends BasePurchase {

    @ManyToOne
    @JoinColumn(name = "purchase_target_id", nullable = false)
    private Product product; //구매상품

    @OneToMany(mappedBy = "productPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPassenger> productPassengers = new ArrayList<>();

}
