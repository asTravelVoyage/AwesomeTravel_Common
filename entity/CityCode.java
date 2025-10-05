package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CityCode {

    @Id
    private String airportCode;

    private String airportEng;
    private String cityCode;
    private String cityEng;
    private Long utcOffsetMins;

    @ManyToOne
    @JoinColumn(name = "country_code")
    private CountryCode countryCode;
}
