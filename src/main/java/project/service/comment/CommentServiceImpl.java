package project.service.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.CommentNotFoundException;
import project.errorhandling.exception.InsufficientRoleException;
import project.errorhandling.exception.InvalidStatusException;
import project.persistence.entity.Comment;
import project.persistence.entity.News;
import project.persistence.entity.UserEntity;
import project.persistence.repository.CommentRepository;
import project.persistence.repository.NewsRepository;
import project.service.Roles;
import project.service.Status;
import project.service.news.NewsService;

@Service
public class CommentServiceImpl implements CommentService {

    private final NewsService newsService;

    private CommentRepository commentRepository;

    private NewsRepository newsRepository;

    @Autowired
    public CommentServiceImpl(NewsService newsService, CommentRepository commentRepository, NewsRepository newsRepository) {
        this.newsService = newsService;
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public Comment create(Comment comment, UserEntity user ) {
        News news = newsService.findByIdAndStatus(comment.getNewsId(),Status.PUBLISHED);

        if(Roles.JOURNALIST.equals(user.getRole()) ||Roles.SUPERVISOR.equals(user.getRole())){
            comment.setUsername(user.getUsername());
            comment.setName(user.getFullName());
        }
        comment.setCreatedDate(LocalDateTime.now());
        comment.setStatus(Status.CREATED);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, String content, UserEntity user) {

        if(Roles.SUPERVISOR.equals(user.getRole())){
            Comment comment = findById(id,user);
            if (Status.CREATED.equals(comment.getStatus())) {
                comment.setContent(content);
                return commentRepository.save(comment);
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();
    }

    @Override
    public Comment findById(Long id, UserEntity user) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            if (Roles.VISITOR.equals(user.getRole()) && Status.APPROVED.equals(comment.getStatus())) {
                return comment;
            } else if (Roles.JOURNALIST.equals(user.getRole()) && Status.APPROVED.equals(comment.getStatus()) || comment.getUsername().equals(user.getUsername())) {
                return comment;
            } else {
                return comment;
            }

        }
        throw new CommentNotFoundException(id);
    }

    @Override
    public void approve(Long commentId, Long newsId, UserEntity user) {
        if(Roles.SUPERVISOR.equals(user.getRole())){
            Comment comment = findById(commentId,user);

            News news = newsService.findById(newsId);

            if (Status.CREATED.equals(comment.getStatus())) {
                //update comment
                comment.setStatus(Status.APPROVED);
                commentRepository.save(comment);

                //update news
                news.getComments().add(commentId);
                newsRepository.save(news);
                return;
            }

            throw new InvalidStatusException();
        }

    }

    @Override
    public void reject(Long id, UserEntity user) {
        Comment comment = findById(id,user);
        if(Roles.SUPERVISOR.equals(user.getRole())){
            if (Status.CREATED.equals(comment.getStatus())) {
                commentRepository.delete(comment);
                return;
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();
    }

    @Override
    public List<Comment> findCommentByNewsId(Long newsId, UserEntity user) {

        List<Comment> comments = commentRepository.findByNewsIdOrderByCreatedDateDesc(newsId);

            if (Roles.VISITOR.equals(user.getRole())) {
                return comments.stream()
                        .filter(comment -> Status.APPROVED.equals(comment.getStatus()))
                               .collect(Collectors.toList());

            } else if (Roles.JOURNALIST.equals(user.getRole()) ) {

                return comments.stream()
                               .filter(comment -> Status.APPROVED.equals(comment.getStatus()) || user.getUsername().equals(comment.getUsername()))
                               .collect(Collectors.toList());
            } else {
                return comments;
            }

        }
}
