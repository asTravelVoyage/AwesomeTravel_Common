package renewal.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.CountryCode;

public interface CountryCodeRepository extends JpaRepository<CountryCode, String> {
}