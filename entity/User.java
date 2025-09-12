package renewal.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
@DynamicUpdate
public class User extends AuditingFields{

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

    // @PrePersist
    // protected void onCreate() {
    //     this.createdAt = LocalDateTime.now();
    //     this.status = this.status == null ? UserStatus.ACTIVE : this.status;
    // }

    // @PreUpdate
    // protected void onUpdate() {
    //     this.updatedAt = LocalDateTime.now();
    // }

    public enum UserStatus {
        ACTIVE, WITHDRAWN, BANNED
    }

    public enum UserRole {
        USER, ADMIN
    }

    public enum UserProvider {
        LOCAL, GOOGLE, NAVER, KAKAO
    }

}
