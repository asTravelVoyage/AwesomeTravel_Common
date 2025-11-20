package renewal.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import renewal.common.entity.PassengerAir;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseAir extends PurchaseBase {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "purchase_air_passengers",
            joinColumns = @JoinColumn(name = "purchase_air_id"),
            inverseJoinColumns = @JoinColumn(name = "passengers_id")
    )
    private List<PassengerAir> passengers = new ArrayList<>();

}
