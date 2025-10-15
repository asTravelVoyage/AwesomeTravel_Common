package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "banner")
public class Banner extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, length = 512)
    private String file;

    @Column(nullable = false, length = 512)
    private String url;

    public Banner(Integer displayOrder, String title, LocalDate startDate, LocalDate endDate, String file, String url) {
        this.displayOrder = displayOrder;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.file = file;
        this.url = url;
        this.active = true;
    }
}

