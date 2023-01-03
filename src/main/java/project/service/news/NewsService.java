package project.service.news;

import java.util.List;
import project.persistence.entity.News;
import project.persistence.entity.UserEntity;

public interface NewsService {

    News create(News news, UserEntity loggedUser);

    News update(News news, Long id, UserEntity loggedUser);

    News findById(Long id, UserEntity loggedUser);

    List<News> findByTitleOrContent(String title, String content, UserEntity loggedUser);

    void submit(Long id, UserEntity loggedUser);

    void approve(Long id, UserEntity loggedUser);

    void reject(Long id, String rejectionReason, UserEntity loggedUser);

    void publish(Long id, UserEntity loggedUser);

    News findByIdAndStatus(Long id, String status);
}