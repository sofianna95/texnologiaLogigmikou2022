package project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.api.ApplicationAPI;
import project.persistence.entity.Comment;
import project.persistence.entity.News;
import project.persistence.entity.Subject;
import project.persistence.entity.UserEntity;
import project.service.comment.CommentService;
import project.service.news.NewsService;
import project.service.subject.SubjectService;
import project.service.user.UserService;

@RestController
public class ApplicationController implements ApplicationAPI {

    private final SubjectService subjectService;

    private final NewsService newsService;

    private final CommentService commentService;

    private final UserService userService;

    @Autowired
    public ApplicationController(SubjectService subjectService, NewsService newsService, CommentService commentService,
            UserService userService) {
        this.subjectService = subjectService;
        this.newsService = newsService;
        this.commentService = commentService;
        this.userService = userService;
    }


    public ResponseEntity<Subject> createSubject(Subject subject, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        return new ResponseEntity<>(subjectService.create(subject, loggedUser), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Subject> updateSubject(Long id, Subject subject, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(subjectService.update(subject, id, loggedUser), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> submitSubject(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        subjectService.approve(id, loggedUser);
        return new ResponseEntity<>("Subject approved", HttpStatus.OK);
    }

    public ResponseEntity<String> rejectSubject(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        subjectService.reject(id,loggedUser);
        return new ResponseEntity<>("Subject rejected", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> findSubjectById(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(subjectService.findById(id, loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Subject>> findAll(String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(subjectService.findAll(loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Subject>> findByName(String name, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(subjectService.findByName(name, loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<News> createNews(News news, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        return new ResponseEntity<>(newsService.create(news,loggedUser), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<News> updateNews(Long id, News news, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        return new ResponseEntity<>(newsService.update(news, id,loggedUser), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> submitNews(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        newsService.submit(id,loggedUser);
        return new ResponseEntity<>("News submitted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> approveNews(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        newsService.approve(id,loggedUser);
        return new ResponseEntity<>("News approved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> rejectNews(Long id, String rejectionReason, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        newsService.reject(id, rejectionReason,loggedUser);
        return new ResponseEntity<>("News rejected", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> publishNews(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        newsService.publish(id,loggedUser);
        return new ResponseEntity<>("News published", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<News>> findNewsByTitleOrContent(String title, String content, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        return new ResponseEntity<>(newsService.findByTitleOrContent(title, content,loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<News> findNewsById(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);
        return new ResponseEntity<>(newsService.findById(id,loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comment> createComment(Comment comment, String username, String password) {

        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(commentService.create(comment, loggedUser), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Comment> updateComment(Long id, String content, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(commentService.update(id, content, loggedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> approveComment(Long commentId, Long newsId, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        commentService.approve(commentId, newsId, loggedUser);
        return new ResponseEntity<>("Comment approved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> rejectComment(Long id, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        commentService.reject(id, loggedUser);
        return new ResponseEntity<>("Comment reject", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Comment>> findCommentByNewsId(Long newsId, String username, String password) {
        UserEntity loggedUser = userService.login(username, password);

        return new ResponseEntity<>(commentService.findCommentByNewsId(newsId, loggedUser), HttpStatus.OK);

    }
}
