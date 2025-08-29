package renewal.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.CityCode;

public interface CityCodeRepository extends JpaRepository<CityCode, String> {
}