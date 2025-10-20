package renewal.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule{
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long day; // 0일차, 1일차, 2일차 ...

    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private LocalDate date;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    @OrderColumn
    private List<Location> locations = new ArrayList<>();
    
    // // 해당 날짜의 숙소
    // @OneToOne(fetch = FetchType.LAZY, optional = true)
    // @JoinColumn(name = "hotel_id")
    // private Hotel hotel;
}
