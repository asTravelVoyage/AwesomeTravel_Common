package renewal.common.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MenuCode {

    @Id
    @Column(length = 6)
    private String code;

    // thymleaf 렌더용 숫자 코드
    private Long code2;

    private String name; // 표시 이름

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "menu_code"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "menu_code", "target_column", "value" }))
    private List<MenuCodeDetail> details;

    @Embeddable
    @Getter
    @Setter
    public static class MenuCodeDetail {

        @Enumerated(EnumType.STRING)
        private TargetColumn targetColumn;

        private String value;

        public enum TargetColumn {
            ID, COUNTRY, CITY
        }
    }

    @PrePersist
    @PreUpdate
    public void syncCode2() {
        if (code != null) {
            code2 = Long.valueOf(code);
        } else {
            code2 = null;
        }
    }
}
