package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class CityCode {

    @Id
    private String airport_code;
    private String airport_eng;
    private String city_code;
    private String city_eng;
    private Long utc_offset_mins;
    private String country_code;
    private String country_eng;
    private String country_kor;
    private String sub_eng;
    private String sub_kor;
    private String region;

}
