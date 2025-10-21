package renewal.common.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tour extends AuditingFields{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String company;
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country")
  private CountryCode country;
  private Long maxCapacity; // 최대인원[명]
  private Long minCapacity; // 최소출발[명] 

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate; // 출발일 기준 시작일

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate; // 마지막 출발일

  // @OrderColumn
  @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("day ASC")
  private List<Schedule> schedules = new ArrayList<>();
  
  // // 패키지 상품과 연결된 경우
  // @OneToOne(mappedBy = "tour", fetch = FetchType.LAZY)
  // private Product product;
  
  // Alias methods
  public void setProductId(Long productId) {
      // Product 엔티티 관계로 관리되므로 직접 설정 불필요
  }
  
  public Long getCount() {
      return maxCapacity;
  }
  
  // @OneToMany(mappedBy = "seat_class", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  // private List<AirReservation> airReservations = new ArrayList<>();

  private Long priceAdult; // 성인 1인당 투어 가격
  private Long priceYouth; // 성인 1인당 투어 가격
  private Long priceInfant; // 성인 1인당 투어 가격

  private Long hotelPriceSum; // 1인당 숙소 가격 합

  // 검색용 키워드들
  @ElementCollection
  private Set<String> keywords = new HashSet<>();
}
