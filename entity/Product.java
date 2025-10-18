package renewal.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import renewal.common.entity.SeatClass.SeatClassType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private ProductType productType; // INDEPENDENT, PACKAGE

    @OneToOne(optional = false)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private String title;
    private Long price; // 그냥 대표가격[성인기준]임. 실제 가격 조회는 연결된 Tour price* 참조
    private Long cutoffDays; // 특정 출발일 기준 N일전까지 예약 받음
    
    // 상품조회시 조회된 항공권 가격 저장용 필드 (DB 저장 X)
    @Transient
    private Long airPriceAdult;
    @Transient
    private Long airPriceYouth;
    @Transient
    private Long airPriceInfant;

    private Boolean isActive = true; // 상품 활성화 여부 (기본값: true)

    // 이미지 URL들
    @ElementCollection
    private List<String> images = new ArrayList<>();

    // 상품정보
    @ElementCollection
    private List<Info> info = new ArrayList<>();

    // 검색용 키워드들
    @ElementCollection
    private Set<String> keywords = new HashSet<>();

    // 대상 항공권등급
    // @JdbcTypeCode(SqlTypes.JSON)
    // @Column(columnDefinition = "json")
    private Set<SeatClassType> seatClassTypes;

    // 일정표

    // 리뷰 요약
    // private Long totalReview = 0L; // 총 리뷰 수
    // @Column(precision = 3, scale = 2)
    // private BigDecimal avgerageReview = new BigDecimal("0.00"); // 평점 평균
    private Long star1 = 0L; // 1점 리뷰 수
    private Long star2 = 0L; // 2점 리뷰 수
    private Long star3 = 0L; // ...
    private Long star4 = 0L;
    private Long star5 = 0L;

    // private Long[] stars = new Long[]{0L,0L,0L,0L,0L};

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Info {
        private String title; // 제목 (굵게)
        private String content; // 내용
        private String appendix; // 추가내용 (흐리게)
    }

    public void UpdateAvg(int star) {
        // totalReview++;
        switch (star) {
            case 1:
                star1++;
                break;
            case 2:
                star2++;
                break;
            case 3:
                star3++;
                break;
            case 4:
                star4++;
                break;
            case 5:
                star5++;
                break;
        }

        // Long sum = star1 + star2 * 2 + star3 * 3 + star4 * 4 + star5 * 5;
        // avgerageReview = BigDecimal.valueOf(sum)
        //         .divide(
        //                 BigDecimal.valueOf(totalReview),
        //                 2,
        //                 RoundingMode.HALF_UP);
    }

    public enum ProductType {
        INDEPENDENT, PACKAGE
    }
    
    // Review 통계 메서드
    public Long getTotalReviews() {
        return star1 + star2 + star3 + star4 + star5;
    }
    
    public Double getAverageRating() {
        Long total = getTotalReviews();
        if (total == 0) return 0.0;
        return (star1 * 1.0 + star2 * 2.0 + star3 * 3.0 + star4 * 4.0 + star5 * 5.0) / total;
    }
    
    public Double getStarPercentage(int starNumber) {
        Long total = getTotalReviews();
        if (total == 0) return 0.0;
        Long count = 0L;
        switch (starNumber) {
            case 1: count = star1; break;
            case 2: count = star2; break;
            case 3: count = star3; break;
            case 4: count = star4; break;
            case 5: count = star5; break;
        }
        return (count * 100.0) / total;
    }

}
