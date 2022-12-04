package project.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SubjectEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private String name;

    @Column
    private String parentSubject;

    @Column
    @ElementCollection
    private List<String> childSubjects = new ArrayList<>();

    @Column
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

    public String getParentSubject() {
        return parentSubject;
    }

    public void setParentSubject(String parentSubject) {
        this.parentSubject = parentSubject;
    }

    public List<String> getChildSubjects() {
        return childSubjects;
    }

    public void setChildSubjects(List<String> childSubjects) {
        this.childSubjects = childSubjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", name='" + name + '\'' +
                ", parentSubject='" + parentSubject + '\'' +
                ", childSubjects=" + childSubjects +
                ", status='" + status + '\'' +
                '}';
    }
}
