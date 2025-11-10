package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InquiryAnswer extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long inquiryId;
    private Long adminId;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static InquiryAnswer create(Long inquiryId, Long adminId, String content) {
        InquiryAnswer a = new InquiryAnswer();
        a.inquiryId = inquiryId;
        a.adminId = adminId;
        a.content = content;
        a.createdAt = LocalDateTime.now();
        return a;
    }

    public void updateContent(String content) {
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
