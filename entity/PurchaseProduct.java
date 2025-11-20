package renewal.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import renewal.common.entity.PassengerProduct;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProduct extends PurchaseBase {

    @ManyToOne
    @JoinColumn
    private Product product; // 구매상품

    // 날짜 지정시 확정된 Product 정보들 영구저장
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;
    private LocalDateTime departDateTime;
    private LocalDateTime returnDateTime;
    private Long bak;
    private Long il;

    @ManyToOne
    @JoinColumn(name = "handler_id")
    private Handler handler;

    private boolean waiting = false; // 예약대기예약인지 여부 기본 false

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "purchase_product_passengers",
            joinColumns = @JoinColumn(name = "purchase_product_id"),
            inverseJoinColumns = @JoinColumn(name = "passengers_id")
    )
    private List<PassengerProduct> passengers = new ArrayList<>();

}
