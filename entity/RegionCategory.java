package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "region_category")
public class RegionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String code; // 코드 (예: ASIA, EAST_ASIA, SUDOGWON)

    @Column(nullable = false)
    private String name; // 이름 (한글)

    @Column(name = "name_eng")
    private String nameEng; // 이름 (영문)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type; // 타입

    @Column(name = "parent_id")
    private Long parentId; // 부모 카테고리 ID (자기 참조)

    @Column(name = "display_order")
    private Integer displayOrder; // 표시 순서

    @Column(name = "country_code")
    private String countryCode; // 국가 코드 (선택)

    public enum CategoryType {
        CONTINENT("대륙"),
        REGION("지역"),
        COUNTRY("국가"),
        SUB_REGION("지역구"),
        CITY("도시");

        private final String description;

        CategoryType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public RegionCategory(String code, String name, String nameEng, CategoryType type, Long parentId, Integer displayOrder, String countryCode) {
        this.code = code;
        this.name = name;
        this.nameEng = nameEng;
        this.type = type;
        this.parentId = parentId;
        this.displayOrder = displayOrder;
        this.countryCode = countryCode;
    }

    // 기존 생성자 호환 (deprecated)
    public RegionCategory(String name, String type, String countryCode) {
        this.name = name;
        this.type = CategoryType.valueOf(type);
        this.countryCode = countryCode;
        this.displayOrder = 0;
    }

    public void updateRegionCategory(String name, String type, String countryCode) {
        if (name != null) this.name = name;
        if (type != null) this.type = CategoryType.valueOf(type);
        if (countryCode != null) this.countryCode = countryCode;
    }
}
