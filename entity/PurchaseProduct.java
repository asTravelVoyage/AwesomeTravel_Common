package renewal.common.entity;

import java.time.LocalDateTime;

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

}
