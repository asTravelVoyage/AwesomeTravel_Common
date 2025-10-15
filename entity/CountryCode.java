package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@lombok.NoArgsConstructor
public class CountryCode {

    @Id
    private String countryCode;

    private String countryEng;
    private String countryKor;
    private String subEng;
    private String subKor;
    private String region;
    
    // Alias getters for backward compatibility
    public String getCode() {
        return countryCode;
    }
    
    public String getNameKor() {
        return countryKor;
    }
    
    public String getNameEng() {
        return countryEng;
    }
    
    // Alias setters
    public void setNameKor(String nameKor) {
        this.countryKor = nameKor;
    }
    
    public void setNameEng(String nameEng) {
        this.countryEng = nameEng;
    }
    
    // Constructor
    public CountryCode(String code, String nameKor, String nameEng) {
        this.countryCode = code;
        this.countryKor = nameKor;
        this.countryEng = nameEng;
    }
}