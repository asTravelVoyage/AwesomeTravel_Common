package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CountryCode {

    @Id
    private String countryCode;

    private String countryEng;
    private String countryKor;
    private String subEng;
    private String subKor;
    private String region;
}