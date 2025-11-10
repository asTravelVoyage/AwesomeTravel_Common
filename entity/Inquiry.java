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
    private InquiryStatus status;

    public static Inquiry create(User user, String title, String content, InquiryCategory category) {
        Inquiry i = new Inquiry();
        i.user = user;
        i.title = title;
        i.content = content;
        i.category = category;
        i.status = InquiryStatus.PENDING;
        return i;
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
        ORDER, // 주문/결제
        PRODUCT, // 상품
        REFUND, // 환불
        ACCOUNT, // 계정
        ETC // 기타
    }

    public enum InquiryStatus {
        PENDING, // 답변 대기
        ANSWERED, // 답변 완료
        CLOSED // 종료
    }
}
