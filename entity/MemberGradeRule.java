package renewal.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import renewal.common.entity.User.MemberGrade;

@Entity
@Getter
@Setter
public class MemberGradeRule {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    private Integer minUseCountLast1Year;
    private Integer minUseCountLast5Years;
    private Integer minMaxPrice;
    private Integer minTotalPriceLast5Years;

    private int priority;
}
