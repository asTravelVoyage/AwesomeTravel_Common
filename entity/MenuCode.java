package renewal.common.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class MenuCode {

    @Id
    private Long code;

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

