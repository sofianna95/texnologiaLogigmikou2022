package project.service.subject;

import java.util.List;
import project.dto.SubjectDTO;

public interface SubjectService {

    SubjectDTO create(SubjectDTO subjectDTO);

    SubjectDTO update(SubjectDTO subjectDTO, Long id);

    SubjectDTO findById(Long id);

    void approve(Long id);

    void reject(Long id);

    List<SubjectDTO> findAll();

    List<SubjectDTO> findByName(String name);


}