package project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;


public class SubjectDTO {
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Long id;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @NotNull(message = "must not be null")
    private String name;

    private List<String> childSubjects = new ArrayList<>();

    private String parentSubject;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private String status;

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

    public List<String> getChildSubjects() {
        return childSubjects;
    }

    public void setChildSubjects(List<String> childSubjects) {
        this.childSubjects = childSubjects;
    }

    public String getParentSubject() {
        return parentSubject;
    }

    public void setParentSubject(String parentSubject) {
        this.parentSubject = parentSubject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", name='" + name + '\'' +
                ", childSubjects=" + childSubjects +
                ", parentSubject='" + parentSubject + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
