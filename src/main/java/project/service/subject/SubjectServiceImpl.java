package project.service.subject;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.InsufficientRoleException;
import project.errorhandling.exception.InvalidStatusException;
import project.errorhandling.exception.SubjectNotFoundException;
import project.persistence.entity.Subject;
import project.persistence.entity.UserEntity;
import project.persistence.repository.SubjectRepository;
import project.service.Roles;
import project.service.Status;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;


    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject create(Subject subject, UserEntity loggedUser) {

        if (Roles.JOURNALIST.equals(loggedUser.getRole()) || Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            subject.setStatus(Status.CREATED);
            subject.setCreatedDate(LocalDateTime.now());
            subject.setUsername(loggedUser.getUsername());
            return subjectRepository.save(subject);
        }
        throw new InsufficientRoleException();
    }

    @Override
    public Subject update(Subject subject, Long id, UserEntity loggedUser) {

        if (Roles.JOURNALIST.equals(loggedUser.getRole()) || Roles.SUPERVISOR.equals(loggedUser.getRole())) {

            Subject oldSubject = findById(id,loggedUser);
            if (Status.CREATED.equals(oldSubject.getStatus())) {
                oldSubject.setParentSubject(subject.getParentSubject());
                oldSubject.setName(subject.getName());
                return subjectRepository.save(oldSubject);
            }
            throw new InvalidStatusException();

        }
        throw new InsufficientRoleException();
    }


    @Override
    public Subject findById(Long id, UserEntity loggedUser) {

        Optional<Subject> subject;
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            subject = subjectRepository.findById(id);
        } else {
            subject = subjectRepository.findByIdAndStatusOrUsername(id, Status.APPROVED, loggedUser.getUsername());
        }
        if (subject.isPresent()) {
            return subject.get();
        }

        throw new SubjectNotFoundException(id);
    }

    @Override
    @Transactional
    public void approve(Long id, UserEntity loggedUser) {

        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            Subject subject = findById(id,loggedUser);
            if (Status.CREATED.equals(subject.getStatus())) {
                subject.setStatus(Status.APPROVED);
                subject.setId(id);
                subjectRepository.save(subject);
                return;
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();
    }

    @Override
    @Transactional
    public void reject(Long id, UserEntity loggedUser) {
        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            Subject subject = findById(id,loggedUser);
            if (Status.CREATED.equals(subject.getStatus())) {
                subjectRepository.delete(subject);
                return;
            }
            throw new InvalidStatusException();
        }
        throw new InsufficientRoleException();
    }

    @Override
    public List<Subject> findAll(UserEntity loggedUser) {
        List<Subject> allSubjects = subjectRepository.findAllByOrderByStatusDescNameDesc();

        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            return allSubjects;
        }

        return allSubjects.stream().filter(subject -> Status.APPROVED.equals(subject.getStatus())
                                  || loggedUser.getUsername().equals(subject.getUsername()))
                          .collect(Collectors.toList());

    }

    @Override
    public List<Subject> findByName(String name, UserEntity loggedUser) {
        List<Subject> subjects = subjectRepository.findByName(name);

        if (Roles.SUPERVISOR.equals(loggedUser.getRole())) {
            return subjects;
        }

        return subjects.stream()
                       .filter(subject -> Status.APPROVED.equals(subject.getStatus())
                               || loggedUser.getUsername().equals(subject.getUsername()))
                       .collect(Collectors.toList());

    }

    @Override
    public boolean existsAllSubjectsById(List<Long> ids) {
        return subjectRepository.countAllByIdIn(ids).equals(ids.size());
    }

}
