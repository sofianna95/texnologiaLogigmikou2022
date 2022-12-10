package project.service.subject;

import java.util.List;
import project.persistence.entity.Comment;

public interface CommentService {

    Comment create(Comment comment);

    Comment update(Long id, String content);

    Comment findById(Long id);

    void approve(Long commentId, Long newsId);

    void reject(Long id);

    List<Comment> findCommentByNewsId(Long newsId);
}
