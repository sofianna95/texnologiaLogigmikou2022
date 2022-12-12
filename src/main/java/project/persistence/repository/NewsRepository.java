package project.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.persistence.entity.News;
import project.persistence.entity.Subject;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    News findByTitle(String title);

    List<News> findByTitleLikeAndContentLike(String title, String content);

    List<News> findByTitleLike(String title);

    List<News> findByContentLike(String content);

    List<News> findAllByOrderByStatusDescPublicationDateDescCreatedDateDesc();


}
