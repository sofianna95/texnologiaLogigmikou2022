package project.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.persistence.entity.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findByStatus(String status);

    List<Subject> findAllByOrderByStatusDescNameDesc();

    List<Subject> findByName(String name);

    Integer countAllByIdIn(List<Long> ids);

    @Query("select subject from Subject subject where (subject.id= :id and subject.status = :status) or subject.username = :username")
    Optional<Subject> findByIdAndStatusOrUsername(@Param("id") Long id, @Param("status") String status, @Param("username") String username);

}
