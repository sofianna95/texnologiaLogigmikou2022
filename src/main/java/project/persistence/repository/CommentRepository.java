package project.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.persistence.entity.Comment;
import project.persistence.entity.Subject;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {


    List<Comment> findByNewsIdOrderByCreatedDateDesc(Long newsId);
}
