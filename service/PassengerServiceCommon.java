package renewal.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import renewal.awesome_travel.passport.dto.PassportDto;
import renewal.awesome_travel.product.dto.ReservationRequestDto.PassengerDto;
import renewal.common.dto.PassengerResponseDto;
import renewal.common.dto.PassengerUpdateRequestDto;
import renewal.common.entity.Passenger;
import renewal.common.entity.PassengerAir;
import renewal.common.entity.Passenger.AgeGroup;
import renewal.common.entity.Passenger.Sex;
import renewal.common.entity.PassengerProduct;
import renewal.common.repository.CountryCodeRepository;
import renewal.common.repository.PassengerRepository;

@Service
@RequiredArgsConstructor
public class PassengerServiceCommon {

    private final CountryCodeRepository countryCodeRepository;
    private final PassengerRepository passengerRepository;

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

    /**
     * 빈 PassengerAir들을 생성합니다.
     * 
     * @param adultCount 성인 인원 수
     * @param youthCount 청소년 인원 수
     * @param infantCount 유아 인원 수
     * @return 생성된 PassengerAir 리스트
     */
    @Transactional
    public List<PassengerAir> createBlankPassengersAir(int adultCount, int youthCount, int infantCount) {
        List<PassengerAir> passengers = new ArrayList<>();

        for (int i = 0; i < adultCount; i++) {
            PassengerAir passenger = new PassengerAir();
            passenger.setAgeGroup(AgeGroup.ADULT);
            passengers.add((PassengerAir) passengerRepository.save(passenger));
        }

        for (int i = 0; i < youthCount; i++) {
            PassengerAir passenger = new PassengerAir();
            passenger.setAgeGroup(AgeGroup.YOUTH);
            passengers.add((PassengerAir) passengerRepository.save(passenger));
        }

        for (int i = 0; i < infantCount; i++) {
            PassengerAir passenger = new PassengerAir();
            passenger.setAgeGroup(AgeGroup.INFANT);
            passengers.add((PassengerAir) passengerRepository.save(passenger));
        }

        return passengers;
    }

    /**
     * 빈 PassengerProduct들을 생성합니다.
     * 
     * @param adultCount 성인 인원 수
     * @param youthCount 청소년 인원 수
     * @param infantCount 유아 인원 수
     * @return 생성된 PassengerProduct 리스트
     */
    @Transactional
    public List<PassengerProduct> createBlankPassengersProduct(int adultCount, int youthCount, int infantCount) {
        List<PassengerProduct> passengers = new ArrayList<>();

        for (int i = 0; i < adultCount; i++) {
            PassengerProduct passenger = new PassengerProduct();
            passenger.setAgeGroup(AgeGroup.ADULT);
            passengers.add((PassengerProduct) passengerRepository.save(passenger));
        }

        for (int i = 0; i < youthCount; i++) {
            PassengerProduct passenger = new PassengerProduct();
            passenger.setAgeGroup(AgeGroup.YOUTH);
            passengers.add((PassengerProduct) passengerRepository.save(passenger));
        }

        for (int i = 0; i < infantCount; i++) {
            PassengerProduct passenger = new PassengerProduct();
            passenger.setAgeGroup(AgeGroup.INFANT);
            passengers.add((PassengerProduct) passengerRepository.save(passenger));
        }

        return passengers;
    }

    /**
     * PassengerDto 리스트로부터 PassengerProduct 엔티티 리스트를 생성합니다.
     * 
     * @param passengerDtos PassengerDto 리스트
     * @return 생성된 PassengerProduct 리스트
     */
    @Transactional
    public List<PassengerProduct> createPassengersFromDto(List<PassengerDto> passengerDtos) {
        List<PassengerProduct> passengers = new ArrayList<>();

        for (PassengerDto dto : passengerDtos) {
            PassengerProduct passenger = new PassengerProduct();
            passenger.setLastNameKor(dto.getLastNameKor());
            passenger.setFirstNameKor(dto.getFirstNameKor());
            passenger.setBirth(dto.getBirth());
            passenger.setSex(dto.getGender());
            passenger.setNumber(dto.getPhone());
            passenger.setEmail(dto.getEmail());
            passenger.setAgeGroup(dto.getAgeGroup());
            passengers.add((PassengerProduct) passengerRepository.save(passenger));
        }

        return passengers;
    }

    /**
     * PassportDto 리스트로 기존 Passenger들을 업데이트합니다.
     * 
     * @param passportDtos PassportDto 리스트
     * @return 업데이트 완료 여부 (모든 Passenger 정보가 완료되었는지)
     */
    @Transactional
    public boolean updatePassengersFromDto(List<PassportDto> passportDtos) {
        boolean allChecked = true;

        for (PassportDto dto : passportDtos) {
            Passenger passenger = passengerRepository.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("탑승객 ID가 유효하지 않습니다: " + (dto.getId() != null ? dto.getId() : "null")));

            // 여권정보 업데이트
            if (!isBlank(dto.getCountryCode())) {
                passenger.setCountryCode(countryCodeRepository.findByCode(dto.getCountryCode())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 국가 코드입니다: " + dto.getCountryCode())));
            }
            passenger.setPassportNum(dto.getPassportNum());
            passenger.setLastName(dto.getLastName());
            passenger.setFirstName(dto.getFirstName());
            passenger.setLastNameKor(dto.getLastNameKor());
            passenger.setFirstNameKor(dto.getFirstNameKor());
            passenger.setBirth(dto.getBirth());
            passenger.setSex(dto.getSex());

            passenger.setNationality(dto.getNationality());
            passenger.setAuthority(dto.getAuthority());
            passenger.setIssue(dto.getIssue());
            passenger.setExpire(dto.getExpire());

            // 일반정보 업데이트
            passenger.setNumber(dto.getNumber());
            passenger.setEmail(dto.getEmail());
            passenger.setSpecialRequests(dto.getSpecialRequests());
                passenger.setAgeGroup(dto.getAgeGroup());

            // 해당 탑승객 정보 완료 여부 체크
            passenger.checkThisPassenger();
            if (!passenger.isCompleted()) {
                allChecked = false;
            }

            passengerRepository.save(passenger);
        }

        return allChecked;
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