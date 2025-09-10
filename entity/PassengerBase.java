package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class PassengerBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    // 국내 국제 필수 입력값
    @Column(nullable = false)
    protected String name; // 풀네임 (한국인>한글, 외국인>영문)

    @Column(nullable = false)
    protected String number; // 전화번호

    @Column(nullable = false)
    protected String email; // 이메일

    @Column(nullable = false)
    protected LocalDate birth; // 생년월일

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sex sex; // 성별

    @ManyToOne
    @JoinColumn(name = "nationality_code", referencedColumnName = "code", nullable = false)
    protected CountryCode nationality; // 국적 REPUBLIC OF KOREA

    // 국제선
    @Column(nullable = true)
    protected String passportNum; // 여권번호

    @Column(nullable = true)
    protected String lastName; // 영문 성

    @Column(nullable = true)
    protected String firstName; // 영문 이름

    @Column(nullable = true)
    protected LocalDate expire; // 만료일

    public PassengerBase(String name, String number, String email, LocalDate birth, Sex sex, CountryCode nationality,
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

    public enum Sex {
        male,
        female
    }

}
