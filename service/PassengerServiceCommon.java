package renewal.common.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import renewal.common.dto.PassengerResponseDto;
import renewal.common.dto.PassengerUpdateRequestDto;
import renewal.common.entity.Passenger;
import renewal.common.entity.Passenger.AgeGroup;
import renewal.common.entity.Passenger.Sex;
import renewal.common.repository.CountryCodeRepository;

@Service
@RequiredArgsConstructor
public class PassengerServiceCommon {

    private final CountryCodeRepository countryCodeRepository;

    public void validateRequiredFields(PassengerUpdateRequestDto dto) {
        // 필드 누락 여부와 관계없이 저장은 허용하되,
        // 이후 checkThisPassenger() 결과로 completed 상태를 관리합니다.
        if (!hasKoreanName(dto) && isBlank(dto.getNumber())
                && isBlank(dto.getEmail()) && dto.getBirth() == null
                && isBlank(dto.getNationality())) {
            throw new IllegalArgumentException("최소 한 개 이상의 정보를 입력해주세요.");
        }
    }

    public void applyPassengerInfo(Passenger passenger, PassengerUpdateRequestDto dto) {
        applyKoreanName(passenger, dto);

        passenger.setNumber(dto.getNumber());
        passenger.setEmail(dto.getEmail());
        passenger.setBirth(dto.getBirth());

        if (!isBlank(dto.getSex())) {
            try {
                passenger.setSex(Sex.valueOf(dto.getSex().trim().toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 성별 값입니다.");
            }
        } else {
            passenger.setSex(null);
        }

        if (!isBlank(dto.getAgeGroup())) {
            try {
                passenger.setAgeGroup(AgeGroup.valueOf(dto.getAgeGroup().trim().toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 연령 구분 값입니다.");
            }
        }

        passenger.setPassportNum(dto.getPassportNum());
        passenger.setLastName(dto.getLastName());
        passenger.setFirstName(dto.getFirstName());
        passenger.setAuthority(dto.getAuthority());
        passenger.setIssue(dto.getIssue());
        passenger.setExpire(dto.getExpire());
        passenger.setSpecialRequests(dto.getSpecialRequests());

        applyNationality(passenger, dto.getNationality());

        passenger.checkThisPassenger();
    }

    public PassengerResponseDto toResponseDto(Passenger passenger) {
        return PassengerResponseDto.builder()
                .id(passenger.getId())
                .koreanName(buildKoreanName(passenger))
                .lastNameKor(passenger.getLastNameKor())
                .firstNameKor(passenger.getFirstNameKor())
                .number(passenger.getNumber())
                .email(passenger.getEmail())
                .birth(passenger.getBirth())
                .sex(passenger.getSex() != null ? passenger.getSex().name() : null)
                .ageGroup(passenger.getAgeGroup() != null ? passenger.getAgeGroup().name() : null)
                .nationality(passenger.getNationality())
                .countryCode(passenger.getCountryCode() != null ? passenger.getCountryCode().getCode() : null)
                .passportNum(passenger.getPassportNum())
                .lastName(passenger.getLastName())
                .firstName(passenger.getFirstName())
                .authority(passenger.getAuthority())
                .issue(passenger.getIssue())
                .expire(passenger.getExpire())
                .specialRequests(passenger.getSpecialRequests())
                .completed(passenger.isCompleted())
                .build();
    }

    public boolean isPassengerSlotEmpty(Passenger passenger) {
        return !hasKoreanName(passenger)
                && isBlank(passenger.getNumber())
                && isBlank(passenger.getEmail());
    }

    public boolean hasKoreanName(Passenger passenger) {
        return !isBlank(passenger.getLastNameKor()) || !isBlank(passenger.getFirstNameKor());
    }

    public boolean isPassengerInfoComplete(List<? extends Passenger> passengers) {
        return passengers.stream()
                .allMatch(Passenger::isCompleted);
    }

    public String buildKoreanName(Passenger passenger) {
        StringBuilder builder = new StringBuilder();
        if (!isBlank(passenger.getLastNameKor())) {
            builder.append(passenger.getLastNameKor().trim());
        }
        if (!isBlank(passenger.getFirstNameKor())) {
            builder.append(passenger.getFirstNameKor().trim());
        }
        String name = builder.toString();
        return name.isBlank() ? null : name;
    }

    private void applyNationality(Passenger passenger, String nationalityCode) {
        passenger.setCountryCode(null);
        passenger.setNationality(null);

        if (isBlank(nationalityCode)) {
            return;
        }

        var countryCode = countryCodeRepository.findByCode(nationalityCode.trim().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 국적 코드입니다."));
        passenger.setCountryCode(countryCode);
        passenger.setNationality(countryCode.getCode());
    }

    private void applyKoreanName(Passenger passenger, PassengerUpdateRequestDto dto) {
        String lastNameKor = dto.getLastNameKor();
        String firstNameKor = dto.getFirstNameKor();

        if (!hasText(lastNameKor) && !hasText(firstNameKor) && hasText(dto.getKoreanName())) {
            String trimmed = dto.getKoreanName().trim();
            if (trimmed.length() >= 2) {
                lastNameKor = trimmed.substring(0, 1);
                firstNameKor = trimmed.substring(1);
            } else {
                lastNameKor = trimmed;
                firstNameKor = null;
            }
        }

        passenger.setLastNameKor(clean(lastNameKor));
        passenger.setFirstNameKor(clean(firstNameKor));
    }

    private boolean hasKoreanName(PassengerUpdateRequestDto dto) {
        return hasText(dto.getLastNameKor()) && hasText(dto.getFirstNameKor())
                || hasText(dto.getKoreanName());
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String clean(String value) {
        return value == null ? null : value.trim();
    }
}


