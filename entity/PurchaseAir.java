package renewal.common.entity;

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

}
