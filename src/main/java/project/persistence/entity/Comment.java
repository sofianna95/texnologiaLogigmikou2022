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
import javax.validation.constraints.NotNull;

@Entity
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(updatable = false, nullable = false)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Long id;

    @Column
    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Column
    private String name;

    @Column(nullable = false)
    @NotNull(message = "must not be null")
    private String content;

    @Column
    @Schema(accessMode = AccessMode.READ_ONLY)
    private String status;

    @Column(nullable = false)
    @NotNull(message = "must not be null")
    private Long newsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", newsId='" + newsId + '\'' +
                '}';
    }
}
