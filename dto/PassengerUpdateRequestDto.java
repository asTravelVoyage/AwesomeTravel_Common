package renewal.common.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PassengerUpdateRequestDto {

    private Long passengerId;

    // 국문 이름
    private String koreanName; // fallback for full name 입력
    private String lastNameKor;
    private String firstNameKor;

    // 연락처
    private String number;
    private String email;

    // 인적 정보
    private LocalDate birth;
    private String sex;
    private String ageGroup;

    // 여권 정보
    private String nationality;
    private String passportNum;
    private String lastName;
    private String firstName;
    private String authority;
    private LocalDate issue;
    private LocalDate expire;

    // 기타
    private String specialRequests;
}


