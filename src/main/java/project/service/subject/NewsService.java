package project.service.subject;

import java.util.List;
import project.persistence.entity.News;

public interface NewsService {

    News create(News news);

    News update(News news, Long id);

    News findById(Long id);

    List<News> findByTitleOrContent(String title, String content);

    void submit(Long id);

    void approve(Long id);

    void reject(Long id, String rejectionReason);

    void publish(Long id);

    List<News> findAll();

}