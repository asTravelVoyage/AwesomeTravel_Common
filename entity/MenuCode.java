package renewal.common.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MenuCode {

    @Id
    private Long code;

    private String name; // 표시 이름

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "menu_code"))
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
}
