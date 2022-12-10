package project.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.persistence.entity.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findByStatus(String status);

    List<Subject> findAllByOrderByStatusDescNameDesc();

    List<Subject> findByName(String name);

    Integer countAllByIdIn(List<Long> ids);



}
