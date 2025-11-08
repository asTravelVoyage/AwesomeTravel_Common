package renewal.common.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import renewal.common.entity.SeatClass.SeatClassType;

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
    private Long finalPriceAdult;
    private Long finalPriceYouth;
    private Long finalPriceInfant;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;
    private LocalDateTime departDateTime;
    private LocalDateTime returnDateTime;
    private Long bak;
    private Long il;
    private Long adultCount;
    private Long youthCount;
    private Long infantCount;

    @ElementCollection
    private List<ConfirmedSeatClass> finalSeatClasses;

    @ManyToOne
    @JoinColumn(name = "handler_id")
    private Handler handler;

    @Embeddable
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ConfirmedSeatClass {

        private Long airId;

        @Enumerated(EnumType.STRING) // Enum을 DB에 문자열로 저장
        private SeatClassType classType; // 좌석 등급

        private Long priceAdult; // 해당 등급의 당시 가격
        private Long priceYouth; // 해당 등급의 당시 청소년 가격
        private Long priceInfant; // 해당 등급의 당시 영유아 가격

        private Long seatCountAdult;
        private Long seatCountYouth;
        private Long seatCountInfant;

        // Air 부분

        private String flightNumber;

        private String departAirport;
        private String departTerminal;
        private LocalDateTime departDateTime;

        private String arriveAirport;
        private String arriveTerminal;
        private LocalDateTime arriveDateTime;

        private Long flightDuration;

        public ConfirmedSeatClass(SeatClass seatClass, Long seatCountAdult, Long seatCountYouth, Long seatCountInfant) {
            Air air = seatClass.getAir();
            this.airId = air.getId();
            this.classType = seatClass.getClassType();
            this.priceAdult = seatClass.getPriceAdult();
            this.priceYouth = seatClass.getPriceYouth();
            this.priceInfant = seatClass.getPriceInfant();
            this.seatCountAdult = seatCountAdult;
            this.seatCountYouth = seatCountYouth;
            this.seatCountInfant = seatCountInfant;

            this.flightNumber = air.getFlightNumber();

            this.departAirport = air.getDepartAirport().getAirportCode();
            this.departTerminal = air.getDepartTerminal();
            this.departDateTime = air.getDepartDateTime();

            this.arriveAirport = air.getArriveAirport().getAirportCode();
            this.arriveTerminal = air.getArriveTerminal();
            this.arriveDateTime = air.getArriveDateTime();

            this.flightDuration = air.getFlightDuration();
        };
    }

}
