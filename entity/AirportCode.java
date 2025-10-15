package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class AirportCode {

    @Id
    @Column(length = 10)
    private String airportCode; // IATA 공항 코드 (예: ICN, GMP, JFK)

    @Column
    private String airportEng; // 공항명 (영문)

    @Column
    private String airportKor; // 공항명 (한글)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code")
    private CityCode cityCode;

    // @Column(name = "airport_type")
    // @Enumerated(EnumType.STRING)
    // private AirportType airportType; // 공항 유형

    // public enum AirportType {
    //     INTERNATIONAL("국제"),
    //     DOMESTIC("국내"),
    //     BOTH("국제/국내");

    //     private final String description;

    //     AirportType(String description) {
    //         this.description = description;
    //     }

    //     public String getDescription() {
    //         return description;
    //     }
    // }

    public AirportCode(String airportCode, String airportEng, String airportKor, CityCode cityCode) {
        this.airportCode = airportCode;
        this.cityCode = cityCode;
        this.airportEng = airportEng;
        this.airportKor = airportKor;
        // this.airportType = airportType;
    }
}
