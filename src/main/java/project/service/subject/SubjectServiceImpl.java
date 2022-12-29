package project.service.subject;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.InvalidStatusException;
import project.errorhandling.exception.SubjectNotFoundException;
import project.persistence.entity.Subject;
import project.persistence.repository.SubjectRepository;
import project.service.Status;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject create(Subject subject) {
        subject.setStatus(Status.CREATED);
        subject.setCreatedDate(LocalDateTime.now());
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject, Long id) {
        Subject oldSubject = findById(id);
        if (Status.CREATED.equals(oldSubject.getStatus())) {
            subject.setStatus(Status.CREATED);
            subject.setCreatedDate(LocalDateTime.now());
            subject.setId(id);
            return subjectRepository.save(subject);
        }
        throw new InvalidStatusException();
    }


    @Override
    public Subject findById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        }
        throw new SubjectNotFoundException(id);
    }

    @Override
    @Transactional
    public void approve(Long id) {
        Subject subject = findById(id);
        if (Status.CREATED.equals(subject.getStatus())) {
            subject.setStatus(Status.APPROVED);
            subject.setId(id);
            subjectRepository.save(subject);
            return;
        }
        throw new InvalidStatusException();

    }

    @Override
    @Transactional
    public void reject(Long id) {
        Subject subject = findById(id);
        if (Status.CREATED.equals(subject.getStatus())) {
            subjectRepository.delete(subject);
            return;
        }
        throw new InvalidStatusException();
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAllByOrderByStatusDescNameDesc();
    }

    @Override
    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    @Override
    public boolean existsAllSubjectsById(List<Long> ids) {
        return subjectRepository.countAllByIdIn(ids).equals(ids.size());
    }

}
