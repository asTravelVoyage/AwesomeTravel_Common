package renewal.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Airline {

    @Id
    @Column(length = 10)
    private String code; // 예: "KE", "OZ"

    private String nameKor;
    private String nameEng;

    private boolean infantSeatsRequired;
    
    // 이름만 있는 생성자
    public Airline(String airlineName){
        this.code = airlineName;
    }
}

