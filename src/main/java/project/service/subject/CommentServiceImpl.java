package project.service.subject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.CommentNotFoundException;
import project.errorhandling.exception.InvalidStatusException;
import project.persistence.entity.Comment;
import project.persistence.entity.News;
import project.persistence.entity.Subject;
import project.persistence.repository.CommentRepository;
import project.persistence.repository.NewsRepository;
import project.service.Status;

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
    public Comment create(Comment comment) {
        News news = newsService.findById(comment.getNewsId());

        if (news != null) {
            comment.setCreatedDate(LocalDateTime.now());
            comment.setStatus(Status.CREATED);
            return commentRepository.save(comment);
        }
        return comment;
    }

    @Override
    public Comment update(Long id, String content) {
        Comment comment = findById(id);
        if (Status.CREATED.equals(comment.getStatus())) {
            comment.setContent(content);
            return commentRepository.save(comment);
        }
        throw new InvalidStatusException();
    }

    @Override
    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new CommentNotFoundException(id);
    }

    @Override
    public void approve(Long commentId, Long newsId) {
        Comment comment = findById(commentId);
        News news = newsService.findById(newsId);

        //update comment
        comment.setStatus(Status.APPROVED);
        commentRepository.save(comment);

        //update news
        news.setComments(new ArrayList<>(List.of(commentId)));
        newsRepository.save(news);

    }

    @Override
    public void reject(Long id) {
        Comment comment = findById(id);
        if (Status.CREATED.equals(comment.getStatus())) {
            commentRepository.delete(comment);
            return;
        }
        throw new InvalidStatusException();
    }

    @Override
    public List<Comment> findCommentByNewsId(Long newsId) {
        newsService.findById(newsId);

        return commentRepository.findByNewsIdOrderByCreatedDateDesc(newsId);
    }

}
