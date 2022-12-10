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
import project.service.subject.CommentService;
import project.service.subject.NewsService;
import project.service.subject.SubjectService;

@RestController
public class ApplicationController implements ApplicationAPI {

    private final SubjectService subjectService;

    private final NewsService newsService;

    private final CommentService commentService;

    @Autowired
    public ApplicationController(SubjectService subjectService, NewsService newsService, CommentService commentService) {
        this.subjectService = subjectService;
        this.newsService = newsService;
        this.commentService = commentService;
    }


    public ResponseEntity<Subject> createSubject(Subject subject, String username, String password) {
        return new ResponseEntity<>(subjectService.create(subject), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Subject> updateSubject(Long id, Subject subject, String username, String password) {

        return new ResponseEntity<>(subjectService.update(subject, id), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> submitSubject(Long id, String username, String password) {
        subjectService.approve(id);
        return new ResponseEntity<>("Subject approved", HttpStatus.OK);
    }

    public ResponseEntity<String> rejectSubject(Long id, String username, String password) {
        subjectService.reject(id);
        return new ResponseEntity<>("Subject rejected", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> findSubjectById(Long id, String username, String password) {
        return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Subject>> findAll(String username, String password) {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Subject>> findByName(String name, String username, String password) {
        return new ResponseEntity<>(subjectService.findByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<News> createNews(News news, String username, String password) {
        return new ResponseEntity<>(newsService.create(news), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<News> updateNews(Long id, News news, String username, String password) {

        return new ResponseEntity<>(newsService.update(news, id), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> submitNews(Long id, String username, String password) {
        newsService.submit(id);
        return new ResponseEntity<>("News submitted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> approveNews(Long id, String username, String password) {
        newsService.approve(id);
        return new ResponseEntity<>("News approved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> rejectNews(Long id, String rejectionReason, String username, String password) {
        newsService.reject(id,rejectionReason);
        return new ResponseEntity<>("News rejected", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> publishNews(Long id,String username, String password) {
        newsService.publish(id);
        return new ResponseEntity<>("News published", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<News> findNewsById(Long id, String username, String password) {
        return new ResponseEntity<>(newsService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comment> createComment(Comment comment, String username, String password) {
        return new ResponseEntity<>(commentService.create(comment), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Comment> updateComment(Long id ,String content, String username, String password) {
        return new ResponseEntity<>(commentService.update(id, content), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> approveComment(Long commentId ,Long newsId, String username, String password) {
        commentService.approve(commentId, newsId);
        return new ResponseEntity<>("Comment approved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> rejectComment(Long id ,String username, String password) {
        commentService.reject(id);
        return new ResponseEntity<>("Comment reject", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Comment>> findCommentByNewsId(Long newsId ,String username, String password) {
        return new ResponseEntity<>(commentService.findCommentByNewsId(newsId), HttpStatus.OK);

    }
}
