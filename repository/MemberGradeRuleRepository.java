package renewal.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import renewal.common.entity.MemberGradeRule;
import renewal.common.entity.User.MemberGrade;

public interface MemberGradeRuleRepository extends JpaRepository<MemberGradeRule, Long> {

        List<MemberGradeRule> findAllByOrderByPriorityAsc();

        boolean existsByGrade(MemberGrade grade);

}
