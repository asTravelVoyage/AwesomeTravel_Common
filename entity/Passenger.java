package renewal.common.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column
    private AgeGroup ageGroup; // 연령구분

    // ======== 여권 내용 ================
    @ManyToOne
    private CountryCode countryCode; // 국적 REPUBLIC OF KOREA
    private String passportNum; // 여권번호
    private String lastName; // 영문 성
    private String firstName; // 영문 이름
    private String lastNameKor; // 국문 성
    private String firstNameKor; // 국문 이름
    private LocalDate birth; // 생년월일
    @Enumerated(EnumType.STRING)
    private Sex sex; // 성별

    private String nationality; // 국적
    private String authority; // 발급관청
    private LocalDate issue; // 발급일
    private LocalDate expire; // 만료일
    // ======== 여권 내용 ================

    private String email;
    private String number;
    private String specialRequests; // 특별 요구사항 -> 텍스트로 받아서 직원이 확인

    private boolean completed = false;

    public void checkThisPassenger() {
        // 필수 필드 체크
        boolean allSet = countryCode != null
                && passportNum != null && !passportNum.isBlank()
                && lastName != null && !lastName.isBlank()
                && firstName != null && !firstName.isBlank()
                && lastNameKor != null && !lastNameKor.isBlank()
                && firstNameKor != null && !firstNameKor.isBlank()
                && birth != null
                && sex != null
                && nationality != null && !nationality.isBlank()
                && authority != null && !authority.isBlank()
                && issue != null
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
}
