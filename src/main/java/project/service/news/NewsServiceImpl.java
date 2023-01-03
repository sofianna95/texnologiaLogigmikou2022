package project.service.news;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.InsufficientRoleException;
import project.errorhandling.exception.InvalidStatusException;
import project.errorhandling.exception.NewsNotFoundException;
import project.errorhandling.exception.NewsTitleAlreadyExistsException;
import project.errorhandling.exception.SubjectNotFoundException;
import project.persistence.entity.News;
import project.persistence.entity.UserEntity;
import project.persistence.repository.NewsRepository;
import project.service.Roles;
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
    public News create(News news, UserEntity loggedUser) {

        if (Roles.JOURNALIST.equals(loggedUser.getRole()) || Roles.SUPERVISOR.equals(loggedUser.getRole())) {
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
                news.setUsername(loggedUser.getUsername());
                return newsRepository.save(news);
            }
        }

        throw new InsufficientRoleException();
    }

    @Override
    public News update(News news, Long id, UserEntity loggedUser) {

        if (Roles.JOURNALIST.equals(loggedUser.getRole()) || Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            News oldNews = findById(id,loggedUser);
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

        throw new InsufficientRoleException();
    }

    @Override
    public News findById(Long id, UserEntity loggedUser) {

        Optional<News> news;
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            news = newsRepository.findById(id);
        } else {
            news = newsRepository.findByIdAndStatusOrUsername(id, Status.PUBLISHED, loggedUser.getUsername());
        }
        if (news.isPresent()) {
            return news.get();
        }

        throw new NewsNotFoundException(id);
    }

    @Override
    public List<News> findByTitleOrContent(String title, String content, UserEntity loggedUser) {
        List<News> allNews = new ArrayList<>();

        if (title != null && content != null) {
            title = "%" + title + "%";
            content = "%" + content + "%";
            allNews = newsRepository.findByTitleLikeAndContentLike(title, content);
        } else if (content != null) {
            content = "%" + content + "%";
            allNews = newsRepository.findByContentLike(content);
        } else {
            title = "%" + title + "%";
            allNews = newsRepository.findByTitleLike(title);
        }

        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            return allNews;
        } else {
            return allNews.stream().filter(news -> Status.PUBLISHED.equals(news.getStatus())
                                  || news.getUsername().equals(loggedUser.getUsername()))
                          .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public void submit(Long id, UserEntity loggedUser) {
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())|| Roles.JOURNALIST.equals(loggedUser.getRole())) {
            News news = findById(id,loggedUser);

            if (Status.CREATED.equals(news.getStatus())) {
                news.setStatus(Status.SUBMITTED);
                news.setId(id);
                newsRepository.save(news);
                return;
            }

            throw new InvalidStatusException();
        }

        throw new InsufficientRoleException();
    }

    @Override
    @Transactional
    public void approve(Long id, UserEntity loggedUser) {
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            News news = findById(id,loggedUser);

            if (Status.SUBMITTED.equals(news.getStatus())) {
                news.setStatus(Status.APPROVED);
                news.setId(id);
                news.setRejectionReason(null);
                newsRepository.save(news);
                return;
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();

    }



    @Override
    @Transactional
    public void reject(Long id, String rejectionReason, UserEntity loggedUser) {
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {

            News news = findById(id,loggedUser);
            if (Status.SUBMITTED.equals(news.getStatus())) {
                news.setStatus(Status.CREATED);
                news.setId(id);
                news.setRejectionReason(rejectionReason);
                newsRepository.save(news);
                return;
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();
    }

    @Override
    @Transactional
    public void publish(Long id, UserEntity loggedUser) {
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {

            News news = findById(id,loggedUser);
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
        throw new InsufficientRoleException();
    }

    @Override
    public News findByIdAndStatus(Long id, String status) {
        Optional<News> news = newsRepository.findByIdAndStatus(id, status);
        if (news.isPresent()) {
            return news.get();
        }
        throw new NewsNotFoundException(id);
    }
}
