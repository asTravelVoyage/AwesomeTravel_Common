package renewal.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import renewal.common.entity.SeatClass.SeatClassType;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class PurchaseBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected PurchaseStatus purchaseStatus;

    protected Long finalPriceAdult;
    protected Long finalPriceYouth;
    protected Long finalPriceInfant;

    protected Long adultCount;
    protected Long youthCount;
    protected Long infantCount;

    @Column(nullable = false)
    protected Long price; // 결제금액

    @ManyToOne
    @JoinColumn
    protected User user; // 구매자

    // 예약자
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String number;
    @Column(nullable = false)
    protected String email;

    @Column(nullable = false)
    protected LocalDateTime purchaseDate; // 구매일

    @Column
    protected Boolean isTransactionComplete = false; // 결제 완료되었는지

    @Column
    protected LocalDateTime paymentDueDate; // 결제기한

    @Column
    protected Boolean isPassengerInfoComplete = false; // 동승자 정보 입력 끝났는지

    @Column
    protected LocalDateTime passengerInfoDeadline; // 동승자정보 입력 기한

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Passenger> passengers = new ArrayList<>();

    @ElementCollection
    private List<ConfirmedSeatClass> finalSeatClasses;

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

    public enum PurchaseStatus {
        RESERVED, // 예약접수
        CONFIRMED, // 담당자 확인
        HOLDING, // 결제 대기 상태
        PAID, // 결제 완료
        CANCELLED // 결제 실패 또는 시간 초과
    }

}
