package renewal.common.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.CascadeType;
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
  private String country;
  private Long maximumCapacity; // 최대인원[명]
  private Long minimumCapacity; // 최소출발[명] 

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @OrderColumn
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

  public void updateHotelPriceSum(Long newHotelPriceSum){
    hotelPriceSum = newHotelPriceSum;
  }

}
