package renewal.common.entity;

import java.time.LocalDateTime;
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
    private AirportCode departAirport;
    private String departTerminal;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime departDateTime;

    // @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    // @Column(nullable = false)
    // private LocalTime departTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_airport")
    private AirportCode arriveAirport;
    private String arriveTerminal;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
    private List<FlightSegment> flightSegments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirStatus status = AirStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightType flightType = FlightType.DIRECT;

    @OneToMany(mappedBy = "air", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatClass> seatClasses = new ArrayList<>();

    // // 공공데이터용 생성자
    // public Air(String flightNumber, Airline airline, AirportCode departAirport, LocalDate departDateTime, LocalTime departTime,
    //         AirportCode arriveAirport, LocalDate arriveDate, LocalTime arriveTime) {
    //     this.flightNumber = flightNumber;
    //     this.airline = airline;
    //     this.departAirport = departAirport;
    //     this.departDateTime = departDateTime;
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

        @ManyToOne
        @JoinColumn(name = "dep_airport_code")
        private AirportCode departAirport; // 출발공항
        private String departTerminal; // 출발터미널
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime departDateTime; // 출발날짜시간

        private Long flightDuration; // 비행시간 => (도착시간 - 출발시간 으로 계산?)
        
        @ManyToOne
        @JoinColumn(name = "arr_airport_code")
        private AirportCode arriveAirport; // 도착공항
        // private String arriveAirport; // 도착공항
        private String arriveTerminal; // 도착터미널
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime arriveDateTime; // 도착날짜시간

        private Long waitDuration = null; // 경유인 경우(=flightSegments 길이가 2 이상) 
        // 다음 FlightSegment 전까지 대기시간
        // => (전 도착시간 - 다음 출발시간 차이로 계산?)
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
    
    // Alias getters/setters for backward compatibility with templates
    public LocalDateTime getDepartDate() {
        return departDateTime != null ? departDateTime.toLocalDate().atStartOfDay() : null;
    }
    
    public void setDepartDate(LocalDateTime departDate) {
        this.departDateTime = departDate;
    }
    
    public LocalDateTime getDepartTime() {
        return departDateTime != null ? departDateTime.toLocalTime().atDate(java.time.LocalDate.now()) : null;
    }
    
    public void setDepartTime(LocalDateTime departTime) {
        if (departDateTime != null && departTime != null) {
            this.departDateTime = departDateTime.toLocalDate().atTime(departTime.toLocalTime());
        }
    }
    
    public LocalDateTime getArriveDate() {
        return arriveDateTime != null ? arriveDateTime.toLocalDate().atStartOfDay() : null;
    }
    
    public void setArriveDate(LocalDateTime arriveDate) {
        this.arriveDateTime = arriveDate;
    }
    
    public LocalDateTime getArriveTime() {
        return arriveDateTime != null ? arriveDateTime.toLocalTime().atDate(java.time.LocalDate.now()) : null;
    }
    
    public void setArriveTime(LocalDateTime arriveTime) {
        if (arriveDateTime != null && arriveTime != null) {
            this.arriveDateTime = arriveDateTime.toLocalDate().atTime(arriveTime.toLocalTime());
        }
    }
    

}