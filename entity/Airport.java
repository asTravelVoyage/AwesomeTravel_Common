package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "airport")
public class Airport {

    @Id
    @Column(length = 10)
    private String code; // IATA 공항 코드 (예: ICN, GMP, JFK)

    @Column(name = "city_code", length = 10)
    private String cityCode; // 도시 코드

    @Column(name = "country_code", length = 10)
    private String countryCode; // 국가 코드

    @Column(name = "name_kor")
    private String nameKor; // 공항명 (한글)

    @Column(name = "name_eng")
    private String nameEng; // 공항명 (영문)

    @Column(name = "airport_type")
    @Enumerated(EnumType.STRING)
    private AirportType airportType; // 공항 유형

    public enum AirportType {
        INTERNATIONAL("국제"),
        DOMESTIC("국내"),
        BOTH("국제/국내");

        private final String description;

        AirportType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public Airport(String code, String cityCode, String countryCode, String nameKor, String nameEng, AirportType airportType) {
        this.code = code;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.airportType = airportType;
    }
}
