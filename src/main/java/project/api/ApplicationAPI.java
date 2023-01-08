package project.api;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.persistence.entity.Comment;
import project.persistence.entity.News;
import project.persistence.entity.Subject;

@RequestMapping("/application")
public interface ApplicationAPI {


    @PostMapping("/subject/create")
    ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject subjectDTO, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/subject/{id}")
    ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody Subject subjectDTO, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PostMapping("subject/{id}/approve")
    ResponseEntity<String> submitSubject(Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @DeleteMapping("subject/{id}/reject")
    ResponseEntity<String> rejectSubject(Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @GetMapping("subject/{id}")
    ResponseEntity<Subject> findSubjectById(Long id, @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password);

    @GetMapping("subjects")
    ResponseEntity<List<Subject>> findAll(@RequestHeader(value = "username",required = false) String username, @RequestHeader(value = "password", required = false) String password);

    @GetMapping("subjects/{name}")
    ResponseEntity<List<Subject>> findByName(String name, @RequestHeader(value = "username",required = false) String username, @RequestHeader(value = "password",required = false) String password);

    @PostMapping("/news/create")
    ResponseEntity<News> createNews(@Valid @RequestBody News news, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/news/{id}")
    ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News news, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/news/submit/{id}")
    ResponseEntity<String> submitNews(@PathVariable Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @PutMapping("/news/approve/{id}")
    ResponseEntity<String> approveNews(@PathVariable Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @PutMapping("/news/reject/{id}")
    ResponseEntity<String> rejectNews(@PathVariable Long id, @RequestParam String rejectionReason, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/news/publish/{id}")
    ResponseEntity<String> publishNews(@PathVariable Long id, @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password);

    @GetMapping("news/search")
    ResponseEntity<List<News>> findNewsByTitleOrContent(@RequestParam(required = false) String title, @RequestParam(required = false) String content,
            @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password);

    @GetMapping("news/{id}")
    ResponseEntity<News> findNewsById(@PathVariable Long id, @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password);

    @PostMapping("/comment/create")
    ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment, @RequestHeader(value = "username", required = false) String username,
            @RequestHeader(value = "password", required = false) String password);

    @PutMapping("/comment/{id}")
    ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestParam String content, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/comment/approve/{commentId}")
    ResponseEntity<String> approveComment(@PathVariable Long commentId, @RequestParam Long newsId, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @DeleteMapping("/comment/reject/{id}")
    ResponseEntity<String> rejectComment(@PathVariable Long id, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @GetMapping("/search/comment/{newsId}")
    ResponseEntity<List<Comment>> findCommentByNewsId(@PathVariable Long newsId, @RequestHeader(value = "username", required = false) String username,
            @RequestHeader(value = "password", required = false) String password);

}
