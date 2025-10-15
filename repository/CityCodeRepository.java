package renewal.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.CityCode;

public interface CityCodeRepository extends JpaRepository<CityCode, String> {

    List<CityCode> findByCountryCodeCountryCode(String countryCode);


}