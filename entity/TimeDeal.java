package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeDeal extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Long value;

    @Transient
    private Long originalPriceAdult;
    @Transient
    private Long originalPriceYouth;
    @Transient
    private Long originalPriceInfant;

    public enum DiscountType {
        PERCENT,
        ABSOLUTE
    }

    public boolean isActive() {
        return (LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime));
    }
}
