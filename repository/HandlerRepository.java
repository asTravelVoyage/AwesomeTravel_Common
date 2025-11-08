package renewal.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.Handler;

public interface HandlerRepository extends JpaRepository<Handler, Long> {

}