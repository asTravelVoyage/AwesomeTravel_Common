package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
    private String countryCode;
    private String countryEng;
    private String countryKor;
    private String subEng;
    private String subKor;
    private String region;

}
