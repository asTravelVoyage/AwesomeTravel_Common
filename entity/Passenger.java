package renewal.common.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 국내 국제 필수 입력값
    @Column(nullable = false)
    private String name; // 풀네임 (한국인>한글, 외국인>영문)

    @Column(nullable = false)
    private String number; // 전화번호

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private LocalDate birth; // 생년월일

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex; // 성별

    @ManyToOne
    @JoinColumn(name = "nationality_code", nullable = false)
    private CountryCode nationality; // 국적 REPUBLIC OF KOREA

    // 국제선
    @Column(nullable = true)
    private String passportNum; // 여권번호

    @Column(nullable = true)
    private String lastName; // 영문 성

    @Column(nullable = true)
    private String firstName; // 영문 이름

    @Column(nullable = true)
    private LocalDate expire; // 만료일

    private String specialRequests; // 특별 요구사항 -> 텍스트로 받아서 직원이 확인

    public Passenger(String name, String number, String email, LocalDate birth, Sex sex, CountryCode nationality,
            String passportNum, String lastName, String firstName, LocalDate expire) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.birth = birth;
        this.sex = sex;
        this.nationality = nationality;
        this.passportNum = passportNum;
        this.lastName = lastName;
        this.firstName = firstName;
        this.expire = expire;
    }

    public void updateName(String newName){
        name = newName;
    }
    public void updateNumber(String newNumber){
        number = newNumber;
    }
    public void updateEmail(String newEmail){
        email = newEmail;
    }
    public void updateBirth(LocalDate newBirth){
        birth = newBirth;
    }
    public void updateSex(Sex newSex){
        sex = newSex;
    }
    public void updateNationality(CountryCode newCountryCode){
        nationality = newCountryCode;
    }
    public void updatePassportNum(String newPassportNum){
        passportNum = newPassportNum;
    }
    public void updateLastName(String newLastName){
        lastName = newLastName;
    }
    public void updateFirstName(String newFirstName){
        firstName = newFirstName;
    }
    public void updateExpire(LocalDate newExpire){
        expire = newExpire;
    }
    
    // Alias getter/setter for passportNum
    public String getPassport_num() {
        return passportNum;
    }
    
    public void setPassport_num(String passportNum) {
        this.passportNum = passportNum;
    }

    public enum Sex {
        male,
        female
    }

    // @ManyToOne
    // @JoinColumn(name = "purchase", nullable = false)
    // private PurchaseBase purchase;

}
