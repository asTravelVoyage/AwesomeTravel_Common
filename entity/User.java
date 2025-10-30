package renewal.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
@DynamicUpdate
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 로그인 정보
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 255)
    private String password; // 소셜 로그인은 null 가능

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private String profileImage;

    @Column(length = 20)
    private String phone;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserProvider provider; // LOCAL, GOOGLE, NAVER

    @Column(length = 100)
    private String providerId; // 소셜로그인 고유 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role; // USER, ADMIN

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE; // ACTIVE, WITHDRAWN, BANNED

    private LocalDateTime lastLoginAt;

    // 여권 정보 (nullable)
    @Column(length = 20)
    private String passportNumber;

    private LocalDate passportIssuedDate;
    private LocalDate passportExpiryDate;

    @Column(length = 3)
    private String passportCountry; // 국가코드 (예: KOR)

    @Column(length = 50)
    private String englishFirstName;

    @Column(length = 50)
    private String englishLastName;

    private Boolean emailVerified;
    private Boolean marketingConsent;

    @OneToMany(mappedBy = "writer")
    @Builder.Default
    private List<Review> reviewComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PurchaseAir> airPurchases = new ArrayList<>();

    // @OneToMany(mappedBy = "user")
    // private List<PurchaseProduct> productPurchases = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    // 최근 본 상품
    // @OneToMany(mappedBy = "user")
    // @Builder.Default
    // private List<UserRecentProduct> recentProducts = new ArrayList<>();

    // // 찜한 상품
    // @OneToMany(mappedBy = "user")
    // @Builder.Default
    // private List<UserLikedProduct> likedProducts = new ArrayList<>();

    // // 보유 쿠폰
    // @OneToMany(mappedBy = "user")
    // @Builder.Default
    // private List<UserCoupon> userCoupons = new ArrayList<>();

    // 보유 포인트
    private Long point;

    // @PrePersist
    // protected void onCreate() {
    // this.createdAt = LocalDateTime.now();
    // this.status = this.status == null ? UserStatus.ACTIVE : this.status;
    // }

    // @PreUpdate
    // protected void onUpdate() {
    // this.updatedAt = LocalDateTime.now();
    // }

    public enum UserStatus {
        ACTIVE, WITHDRAWN, BANNED
    }

    public enum UserRole {
        USER, ADMIN
    }

    public enum UserProvider {
        LOCAL, GOOGLE, NAVER
    }

    public enum MemberGrade {
        BRONZE, SILVER, GOLD, PLATINUM, VIP
    }

    // updateUserInfo 메서드
    public void updateUserInfo(String email, String password, String name, LocalDate birthDate,
            UserProvider provider, String providerId, UserRole role, UserStatus status,
            String passportNumber, LocalDate passportIssuedDate, LocalDate passportExpiryDate,
            String passportCountry, String englishFirstName, String englishLastName,
            Boolean emailVerified, Boolean marketingConsent) {
        if (email != null)
            this.email = email;
        if (password != null)
            this.password = password;
        if (name != null)
            this.name = name;
        if (birthDate != null)
            this.birthDate = birthDate;
        if (provider != null)
            this.provider = provider;
        if (providerId != null)
            this.providerId = providerId;
        if (role != null)
            this.role = role;
        if (status != null)
            this.status = status;
        if (passportNumber != null)
            this.passportNumber = passportNumber;
        if (passportIssuedDate != null)
            this.passportIssuedDate = passportIssuedDate;
        if (passportExpiryDate != null)
            this.passportExpiryDate = passportExpiryDate;
        if (passportCountry != null)
            this.passportCountry = passportCountry;
        if (englishFirstName != null)
            this.englishFirstName = englishFirstName;
        if (englishLastName != null)
            this.englishLastName = englishLastName;
        if (emailVerified != null)
            this.emailVerified = emailVerified;
        if (marketingConsent != null)
            this.marketingConsent = marketingConsent;
    }

}
