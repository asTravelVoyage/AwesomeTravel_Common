package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class CityCode {

    private String country;

    @Id
    private String code;

    private String kor;
    
    private String eng;
}
