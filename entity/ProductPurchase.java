package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ProductPurchase")
public class ProductPurchase extends BasePurchase {

    @ManyToOne
    @JoinColumn(name = "purchase_target_id", nullable = false)
    private Product product; //구매상품

    @OneToMany(mappedBy = "productPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPassenger> productPassengers = new ArrayList<>();

}
