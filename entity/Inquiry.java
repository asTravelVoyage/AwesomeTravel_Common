package renewal.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inquiry extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status = InquiryStatus.PENDING;

    private Long productId;

    private Long purchaseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStage stage = InquiryStage.GENERAL;

    @Column(nullable = false)
    private boolean isPrivate = true;

    // 기본 생성자
    public Inquiry() {
        this.status = InquiryStatus.PENDING;
    }

    // 생성자
    public Inquiry(
            User user,
            String title,
            String content,
            InquiryCategory category,
            Long productId,
            Long purchaseId,
            InquiryStage stage) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.stage = stage;
    }

    public void markAnswered() {
        this.status = InquiryStatus.ANSWERED;
    }

    public void cancelAnswered() {
        this.status = InquiryStatus.PENDING;
    }

    // Alias method for isAnswered
    public boolean isAnswered() {
        return this.status == InquiryStatus.ANSWERED;
    }

    public enum InquiryCategory {
        ORDER("주문/결제"),
        REFUND("환불/취소"),
        ACCOUNT("계정/회원"),
        BENEFIT("혜택/쿠폰"),
        SERVICE("서비스 오류"),
        POLICY("정책/약관"),
        ETC("기타");

        public String displayName() {
            return displayName;
        }

        private final String displayName;

        InquiryCategory(String displayName) {
            this.displayName = displayName;
        }
    }

    public enum InquiryStatus {
        PENDING, // 답변 대기
        ANSWERED, // 답변 완료
        CLOSED; //

        public String displayName() {
            return switch (this) {
                case PENDING -> "답변 대기";
                case ANSWERED -> "답변 완료";
                case CLOSED -> "종료";
            };
        }
    }

    public enum InquiryStage {
        GENERAL("일반 문의"),
        BEFORE_PURCHASE("구매 전 문의"),
        AFTER_BOOKING("예약/결제 문의"),
        AFTER_TRAVEL("이용 후 문의");

        private final String displayName;

        InquiryStage(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
