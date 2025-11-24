package renewal.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.TimeDeal;

public interface TimeDealRepository extends JpaRepository<TimeDeal, Long> {

}
