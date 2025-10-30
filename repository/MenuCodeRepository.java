package renewal.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.MenuCode;

public interface MenuCodeRepository extends JpaRepository<MenuCode, String> {

    List<MenuCode> findAllByCodeStartingWith(String menuCode);

    MenuCode findByCode(String menuCode);

    Optional<MenuCode> findByCode2(Long id);

    void deleteByCode2(Long id);

}