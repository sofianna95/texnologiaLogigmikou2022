package project.service.subject;

import project.persistence.entity.News;

public interface NewsService {

    News create(News news);

    News update(News news, Long id);

    News findById(Long id);

    void submit(Long id);

    void approve(Long id);

    void reject(Long id, String rejectionReason);

    void publish(Long id);

}