package project.service.news;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.InvalidStatusException;
import project.errorhandling.exception.NewsNotFoundException;
import project.errorhandling.exception.NewsTitleAlreadyExistsException;
import project.errorhandling.exception.SubjectNotFoundException;
import project.persistence.entity.News;
import project.persistence.repository.NewsRepository;
import project.service.Status;
import project.service.subject.SubjectService;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final SubjectService subjectService;


    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, SubjectService subjectService) {
        this.newsRepository = newsRepository;
        this.subjectService = subjectService;
    }

    @Override
    public News create(News news) {
        //check the uniqueness of the title
        News oldNews = newsRepository.findByTitle(news.getTitle());
        boolean subjectsExists = subjectService.existsAllSubjectsById(news.getSubjects());
        if (oldNews != null) {
            throw new NewsTitleAlreadyExistsException(news.getTitle());
        } else if (!subjectsExists) {
            throw new SubjectNotFoundException();
        } else {
            news.setCreatedDate(LocalDateTime.now());
            news.setStatus(Status.CREATED);
            return newsRepository.save(news);
        }

    }

    @Override
    public News update(News news, Long id) {
        News oldNews = findById(id);
        //check the uniqueness of the title (if title has updated)
        if (Status.CREATED.equals(oldNews.getStatus())) {
            if (news.getTitle() != null) {
                News oldNewByTitle = newsRepository.findByTitle(news.getTitle());
                if (oldNewByTitle != null) {
                    throw new NewsTitleAlreadyExistsException(news.getTitle());
                } else {
                    oldNews.setTitle(news.getTitle());
                }
            }
            if (news.getSubjects() != null && !news.getSubjects().isEmpty()) {
                boolean subjectsExists = subjectService.existsAllSubjectsById(news.getSubjects());
                if (!subjectsExists) {
                    throw new SubjectNotFoundException();
                } else {
                    oldNews.setSubjects(news.getSubjects());
                }

            }
            oldNews.setContent(news.getContent());
            return newsRepository.save(oldNews);
        }
        throw new InvalidStatusException();
    }

    @Override
    public News findById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        }
        throw new NewsNotFoundException(id);
    }

    @Override
    public List<News> findByTitleOrContent(String title, String content) {
        if(title != null && content != null){
            title = "%" + title + "%";
            content = "%" + content + "%";
            return newsRepository.findByTitleLikeAndContentLike(title,content);
        }
        else if(content != null){
            content = "%" + content + "%";
            return newsRepository.findByContentLike(content);
        }
        else{
            title = "%" + title + "%";
            return newsRepository.findByTitleLike(title);
        }
    }

    @Override
    @Transactional
    public void submit(Long id) {
        News news = findById(id);

        if (Status.CREATED.equals(news.getStatus())) {
            news.setStatus(Status.SUBMITTED);
            news.setId(id);
            newsRepository.save(news);

            return;
        }

        throw new InvalidStatusException();
    }

    @Override
    @Transactional
    public void approve(Long id) {
        News news = findById(id);

        if (Status.SUBMITTED.equals(news.getStatus())) {
            news.setStatus(Status.APPROVED);
            news.setId(id);
            news.setRejectionReason(null);
            newsRepository.save(news);
            return;
        }
        throw new InvalidStatusException();
    }



    @Override
    @Transactional
    public void reject(Long id,String rejectionReason) {
        News news = findById(id);
        if (Status.SUBMITTED.equals(news.getStatus())) {
            news.setStatus(Status.CREATED);
            news.setId(id);
            news.setRejectionReason(rejectionReason);
            newsRepository.save(news);
            return;
        }
        throw new InvalidStatusException();

    }

    @Override
    @Transactional
    public void publish(Long id) {
        News news = findById(id);
        if (Status.APPROVED.equals(news.getStatus())) {
            news.setStatus(Status.PUBLISHED);
            news.setPublicationDate(LocalDateTime.now());
            news.setId(id);
            news.setRejectionReason(null);
            newsRepository.save(news);
            return;
        }
        throw new InvalidStatusException();

    }

    @Override
    public News findByIdAndStatus(Long id, String status) {
        Optional<News> news = newsRepository.findByIdAndStatus(id,status);
        if (news.isPresent()) {
            return news.get();
        }
        throw new NewsNotFoundException(id);
    }
}
