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
    @Column
    private String name; // 풀네임 (한국인>한글, 외국인>영문)

    @Column
    private LocalDate birth; // 생년월일

    @Column
    @Enumerated(EnumType.STRING)
    private Sex sex; // 성별

    @Column
    private String number; // 전화번호

    @Column
    private String email; // 이메일

    @Column
    private AgeGroup ageGroup; // 연령구분

    @ManyToOne
    @JoinColumn(name = "nationality_code", nullable = true)
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

    private boolean completed = false;

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

    public void checkThisPassenger() {
        // 필수 필드 체크
        boolean allSet = name != null && !name.isBlank()
                && number != null && !number.isBlank()
                && email != null && !email.isBlank()
                && birth != null
                && sex != null
                && ageGroup != null
                && nationality != null
                && passportNum != null && !passportNum.isBlank()
                && lastName != null && !lastName.isBlank()
                && firstName != null && !firstName.isBlank()
                && expire != null;

        this.completed = allSet;
    }

    public enum Sex {
        MALE,
        FEMALE
    }

    public enum AgeGroup {
        INFANT,
        YOUTH,
        ADULT;

        public String displayName() {
            return switch (this) {
                case INFANT -> "유아";
                case YOUTH -> "청소년";
                case ADULT -> "성인";
            };
        }
    }

    // @ManyToOne
    // @JoinColumn(name = "purchase", nullable = false)
    // private PurchaseBase purchase;

}
