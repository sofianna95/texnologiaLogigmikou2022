package project.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.persistence.entity.News;
import project.persistence.entity.Subject;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    News findByTitle(String title);

    Optional<News> findByIdAndStatus(Long id, String status);

    List<News> findByTitleLikeAndContentLike(String title, String content);

    List<News> findByTitleLike(String title);

    List<News> findByContentLike(String content);

    List<News> findAllByOrderByStatusDescPublicationDateDescCreatedDateDesc();

    @Query("select news from News news where news.id = :id and (news.status = :status or news.username = :username)")
    Optional<News> findByIdAndStatusOrUsername(@Param("id") Long id,@Param("status") String status,@Param("username") String username );


}
