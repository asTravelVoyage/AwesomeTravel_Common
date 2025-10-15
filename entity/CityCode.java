package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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
    
    // Alias getters for backward compatibility
    
    public String getKor() {
        return cityKor;
    }
    
    public String getEng() {
        return cityEng;
    }
    
    public CountryCode getCountry() {
        return countryCode;
    }
    
    // Alias setters
    public void setKor(String kor) {
        this.cityKor = kor;
    }
    
    public void setEng(String eng) {
        this.cityEng = eng;
    }
    
    public void setCountry(CountryCode country) {
        this.countryCode = country;
    }
    
    // // Constructor
    // public CityCode(String cityCode, String cityEng, String cityKor, Long utcOffsetMins, CountryCode countryCode) {
    //     this.cityKor = kor;
    //     this.cityEng = eng;
    //     // countryCode는 별도로 설정
    // }
}
