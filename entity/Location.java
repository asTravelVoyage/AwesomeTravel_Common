package renewal.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
    // public void updateSchedule (Schedule newSchedule){
    // schedule = newSchedule;
    // }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private CityCode cityCode;
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private LocalDate date;

    // ==============Type이 AIR면 사용할 필드===============
    // @ManyToOne
    // @JoinColumn(name = "seatClass_id")

    @Transient
    private SeatClass seatClass; // 상품 조회시 조건에 맞는 SeatClass id를 지정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depart_airport")
    private AirportCode departAirport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_airport")
    private AirportCode arriveAirport;
    // ==============Type이 POINT면 사용할 필드===============
    // private String country;

    // ==============Type이 HOTEL이면 사용할 필드===============
    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public enum LocationType {
        POINT,
        AIR,
        HOTEL
    }
}
