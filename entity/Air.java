package renewal.common.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Air extends AuditingFields {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_code", nullable = false)
    private Airline airline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depart_airport")
    private CityCode departAirport;
    private String departTerminal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDateTime departDateTime;

    // @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    // @Column(nullable = false)
    // private LocalTime departTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_airport")
    private CityCode arriveAirport;
    private String arriveTerminal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDateTime arriveDateTime;

    // @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    // @Column(nullable = false)
    // private LocalTime arriveTime;

    @Column(nullable = false)
    private Long flightDuration;

    @Column(nullable = false)
    private Integer stopovers = 0; // 경유 횟수 (0 = 직항, 1 이상 = 경유)

    @ElementCollection
    private List<FlightSegment> flightSegmentList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirStatus status = AirStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightType flightType = FlightType.DIRECT;

    @OneToMany(mappedBy = "air", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatClass> seatClasses = new ArrayList<>();

    // // 공공데이터용 생성자
    // public Air(String flightNumber, Airline airline, CityCode departAirport, LocalDate departDate, LocalTime departTime,
    //         CityCode arriveAirport, LocalDate arriveDate, LocalTime arriveTime) {
    //     this.flightNumber = flightNumber;
    //     this.airline = airline;
    //     this.departAirport = departAirport;
    //     this.departDate = departDate;
    //     this.departTime = departTime;
    //     this.arriveAirport = arriveAirport;
    //     this.arriveDate = arriveDate;
    //     this.arriveTime = arriveTime;
    // }

    public Air updateAir(Integer stopovers, List<SeatClass> seatClasses) {
        this.status = AirStatus.ACTIVE;
        this.stopovers = stopovers;
        if (stopovers == 0) {
            this.flightType = FlightType.DIRECT;
        } else {
            this.flightType = FlightType.STOP_OVER;
        }
        this.seatClasses = seatClasses;
        return this;
    }

    @Embeddable
    @Getter
    @Setter
    public static class FlightSegment {

        private CityCode departAirport; // 출발공항
        private String departTerminal; // 출발터미널
        private LocalDateTime departDateTime; // 출발날짜시간

        private Long flightDuration; // 비행시간 => (도착시간 - 출발시간 으로 계산?)

        private CityCode arriveAirport; // 도착공항
        private String arriveTerminal; // 도착터미널
        private LocalDateTime arriveDateTime; // 도착날짜시간

        private Long waitDuration = null; // 경유인 경우(=flightSegments 길이가 2 이상) 다음
        // FlightSegment 전까지 대기시간
        // => (전 도착시간 - 다음 출발시간 차이로 계산?)
        
        // public FlightSegment(){

        // // ==== 비행시간 계산 ====
        // // 1) 현지시간(LocalDateTime) + 공항 UTC 오프셋 → UTC 기준 Instant
        // ZoneOffset departOffset = ZoneOffset.ofTotalSeconds(
        //         (int) (departAirport.getUtcOffsetMins() * 60));
        // ZoneOffset arriveOffset = ZoneOffset.ofTotalSeconds(
        //         (int) (arriveAirport.getUtcOffsetMins() * 60));

        // Instant departUtc = departDateTime.toInstant(departOffset);
        // Instant arriveUtc = arriveDateTime.toInstant(arriveOffset);

        // // 2) 두 Instant 차이를 분 단위로 계산
        // this.flightDuration = Duration.between(departUtc, arriveUtc).toMinutes();

        // }
        // 별도의 계산 메서드
        public void calculateFlightDuration() {
            if (departAirport == null || arriveAirport == null ||
                departDateTime == null || arriveDateTime == null) {
                this.flightDuration = null;
                return;
            }

            ZoneOffset departOffset = ZoneOffset.ofTotalSeconds((int) (departAirport.getUtcOffsetMins() * 60));
            ZoneOffset arriveOffset = ZoneOffset.ofTotalSeconds((int) (arriveAirport.getUtcOffsetMins() * 60));

            Instant departUtc = departDateTime.toInstant(departOffset);
            Instant arriveUtc = arriveDateTime.toInstant(arriveOffset);

            this.flightDuration = Duration.between(departUtc, arriveUtc).toMinutes();
        }
    }

    public enum AirStatus {
        ACTIVE, // 사용 가능
        INACTIVE, // 비활성 (운항 종료)
        HIDDEN // 프론트에 노출되지 않음
    }

    public enum FlightType {
        DIRECT,
        STOP_OVER
    }

}