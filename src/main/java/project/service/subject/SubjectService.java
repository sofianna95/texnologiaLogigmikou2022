package project.service.subject;

import java.util.List;
import project.persistence.entity.Subject;

public interface SubjectService {

    Subject create(Subject subject);

    Subject update(Subject subject, Long id);

    Subject findById(Long id);

    void approve(Long id);

    void reject(Long id);

    List<Subject> findAll();

    List<Subject> findByName(String name);

    boolean existsAllSubjectsById(List<Long> ids);

}