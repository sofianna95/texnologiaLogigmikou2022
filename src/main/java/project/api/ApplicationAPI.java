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
import project.dto.SubjectDTO;

@RequestMapping("/application")
public interface ApplicationAPI {


    @PostMapping("/subject/create")
    ResponseEntity<SubjectDTO> create(@Valid @RequestBody SubjectDTO subjectDTO, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PutMapping("/subject/{id}")
    ResponseEntity<SubjectDTO> update(@PathVariable Long id, @Valid @RequestBody SubjectDTO subjectDTO, @RequestHeader("username") String username,
            @RequestHeader("password") String password);

    @PostMapping("subject/{id}/approve")
    ResponseEntity<String> submit(Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @DeleteMapping("subject/{id}/reject")
    ResponseEntity<String> reject(Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @GetMapping("subject/{id}")
    ResponseEntity<SubjectDTO> findById(Long id, @RequestHeader("username") String username, @RequestHeader("password") String password);

    @GetMapping("subjects")
    ResponseEntity<List<SubjectDTO>> findAll(@RequestHeader("username") String username, @RequestHeader("password") String password);

    @GetMapping("subjects/{name}")
    ResponseEntity<List<SubjectDTO>> findByName(String name,@RequestHeader("username") String username, @RequestHeader("password") String password);

}
