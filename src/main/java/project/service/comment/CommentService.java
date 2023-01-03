package project.service.comment;

import java.util.List;
import project.persistence.entity.Comment;
import project.persistence.entity.UserEntity;

public interface CommentService {

    Comment create(Comment comment, UserEntity user);

    Comment update(Long id, String content, UserEntity user);

    Comment findById(Long id, UserEntity user);

    void approve(Long commentId, Long newsId, UserEntity user);

    void reject(Long id, UserEntity user);

    List<Comment> findCommentByNewsId(Long newsId, UserEntity user);
}
