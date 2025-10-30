package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
public class CityCode {

    // private String airportCode;

    // private String airportEng;
    // private String airportKor;
    @Id
    private String cityCode;
    private String cityEng;
    private String cityKor;
    private Long utcOffsetMins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private CountryCode countryCode;

}
