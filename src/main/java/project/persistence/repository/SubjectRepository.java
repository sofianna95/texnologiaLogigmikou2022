package project.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.persistence.entity.SubjectEntity;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {

    List<SubjectEntity> findByStatus(String status);

    List<SubjectEntity> findAllByOrderByStatusDescNameDesc();

    List<SubjectEntity> findByName(String name);

}
