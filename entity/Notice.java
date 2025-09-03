package renewal.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notice extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean fix;

    @Column
    private Integer priority; // fix == true일 때 우선순위 지정

    @Column
    private String imageUrl; // 공지에 들어갈 대표 이미지 URL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeCategory category;

    @Column
    private LocalDateTime startAt; // 공지 노출 시작 시간

    @Column
    private LocalDateTime endAt; // 공지 노출 종료 시간

    @Column(nullable = false)
    private boolean isVisible = true;

    public Notice(String title, String content, Boolean fix, Integer priority,
            String imageUrl, NoticeCategory category,
            LocalDateTime startAt, LocalDateTime endAt) {
        this.title = title;
        this.content = content;
        this.fix = fix;
        this.priority = priority;
        this.imageUrl = imageUrl;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Getter
    public enum NoticeCategory {
        EVENT("이벤트"),
        UPDATE("업데이트"),
        SYSTEM("점검"),
        GUIDE("안내");

        private final String displayName;

        NoticeCategory(String displayName) {
            this.displayName = displayName;
        }
    }

    public enum SearchType {
        TITLE,
        CONTENT,
        TITLE_CONTENT
    }

    // ==> 백오피스 Service로 분리
    // public void update(NoticeRequestDto dto) {
    // this.title = dto.getTitle();
    // this.content = dto.getContent();
    // this.fix = dto.getFix();
    // this.priority = dto.getPriority();
    // this.imageUrl = dto.getImageUrl();
    // this.category = dto.getCategory();
    // this.startAt = dto.getStartAt();
    // this.endAt = dto.getEndAt();
    // }

    // public void updatePartially(NoticeRequestDto dto) {
    // if (dto.getTitle() != null) this.title = dto.getTitle();
    // if (dto.getContent() != null) this.content = dto.getContent();
    // if (dto.getFix() != null) this.fix = dto.getFix();
    // if (dto.getPriority() != null) this.priority = dto.getPriority();
    // if (dto.getImageUrl() != null) this.imageUrl = dto.getImageUrl();
    // if (dto.getCategory() != null) this.category = dto.getCategory();
    // if (dto.getStartAt() != null) this.startAt = dto.getStartAt();
    // if (dto.getEndAt() != null) this.endAt = dto.getEndAt();
    // }

    // public void setFix(Boolean fix) {
    // this.fix = fix;
    // }

}
