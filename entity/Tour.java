package renewal.common.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  // @OrderColumn
  @OrderBy("day ASC")
  private List<Schedule> schedules = new ArrayList<>();
  
  // 패키지 상품과 연결된 경우
  @OneToOne(mappedBy = "tour", fetch = FetchType.LAZY)
  private Product product;
  
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
