package project.service.subject;

import java.util.List;
import project.persistence.entity.Subject;
import project.persistence.entity.UserEntity;

public interface SubjectService {

    Subject create(Subject subject, UserEntity loggedUser);

    Subject update(Subject subject, Long id, UserEntity loggedUser);

    Subject findById(Long id, UserEntity loggedUser);

    void approve(Long id, UserEntity loggedUser);

    void reject(Long id, UserEntity loggedUser);

    List<Subject> findAll(UserEntity loggedUser);

    List<Subject> findByName(String name, UserEntity loggedUser);

    boolean existsAllSubjectsById(List<Long> ids);

}