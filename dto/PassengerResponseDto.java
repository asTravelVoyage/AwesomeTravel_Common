package renewal.common.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerResponseDto {

    private Long id;

    private String koreanName;
    private String lastNameKor;
    private String firstNameKor;

    private String number;
    private String email;

    private LocalDate birth;
    private String sex;
    private String ageGroup;

    private String nationality;
    private String countryCode;
    private String passportNum;
    private String lastName;
    private String firstName;
    private String authority;
    private LocalDate issue;
    private LocalDate expire;

    private String specialRequests;
    private boolean completed;
}


