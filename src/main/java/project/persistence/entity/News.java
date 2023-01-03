package project.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class News {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(updatable = false, nullable = false)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "must not be null")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "must not be null")
    private String content;

    @Column(nullable = false)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Column
    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime publicationDate;

    @Column
    @Schema(accessMode = AccessMode.READ_ONLY)
    private String status;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull(message = "must not be null")
    @NotEmpty(message = "must not be empty")
    @Column(nullable = false)
    private List<Long> subjects = new ArrayList<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private List<Long> comments = new ArrayList<>();

    @Column
    @Schema(accessMode = AccessMode.READ_ONLY)
    private String rejectionReason;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Long> subjects) {
        this.subjects = subjects;
    }

    public List<Long> getComments() {
        return comments;
    }

    public void setComments(List<Long> comments) {
        this.comments = comments;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", publicationDate=" + publicationDate +
                ", status='" + status + '\'' +
                ", subjects=" + subjects +
                ", comments=" + comments +
                ", rejectionReason=" + rejectionReason +
                '}';
    }
}
