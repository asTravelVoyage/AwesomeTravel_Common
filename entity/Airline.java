package renewal.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Airline {

    @Id
    @Column(length = 10)
    private String code; // 예: "KE", "OZ"

    private String nameKor;
    private String nameEng;

    private boolean infantSeatsRequired;

    private String iconUrl;
    
    // 이름만 있는 생성자
    public Airline(String airlineName){
        this.code = airlineName;
    }
    
    // 전체 필드 생성자
    public Airline(String code, String nameKor, String nameEng, boolean infantSeatsRequired){
        this.code = code;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.infantSeatsRequired = infantSeatsRequired;
    }
}

