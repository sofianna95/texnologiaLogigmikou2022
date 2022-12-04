package project.service.shoppingCard;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dto.SubjectDTO;
import project.errorhandling.exception.InvalidStatusException;
import project.errorhandling.exception.SubjectNotFoundException;
import project.mapper.SubjectMapper;
import project.persistence.entity.SubjectEntity;
import project.persistence.repository.SubjectRepository;
import project.service.Constants;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;



    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }



    @Override
    public SubjectDTO create(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = SubjectMapper.mapDTOToEntity(subjectDTO);
        subjectEntity.setStatus(Constants.CREATED);
        subjectEntity.setCreatedDate(LocalDateTime.now());
        return SubjectMapper.mapEntityToDTO(subjectRepository.save(subjectEntity));
    }

    @Override
    public SubjectDTO update(SubjectDTO subjectDTO, Long id) {
        SubjectDTO oldSubject = findById(id);
        if (Constants.CREATED.equals(oldSubject.getStatus())) {
            SubjectEntity subjectEntity = SubjectMapper.mapDTOToEntity(subjectDTO);
            subjectEntity.setStatus(Constants.CREATED);
            subjectEntity.setCreatedDate(LocalDateTime.now());
            subjectEntity.setId(id);
            return SubjectMapper.mapEntityToDTO(subjectRepository.save(subjectEntity));
        }
        throw new InvalidStatusException();
    }


    @Override
    public SubjectDTO findById(Long id) {
        Optional<SubjectEntity> shoppingCardEntity = subjectRepository.findById(id);
        if (shoppingCardEntity.isPresent()) {
            return SubjectMapper.mapEntityToDTO(shoppingCardEntity.get());
        }
        throw new SubjectNotFoundException(id);
    }

    @Override
    @Transactional
    public void approve(Long id) {
        SubjectDTO subjectDTO = findById(id);
        if (Constants.CREATED.equals(subjectDTO.getStatus())) {
            subjectDTO.setStatus(Constants.APPROVED);
            SubjectEntity subjectEntity = SubjectMapper.mapDTOToEntity(subjectDTO);
            subjectEntity.setId(id);
            subjectRepository.save(subjectEntity);
            return;
        }
        throw new InvalidStatusException();

    }

    @Override
    @Transactional
    public void reject(Long id) {
        SubjectDTO subjectDTO = findById(id);
        if (Constants.CREATED.equals(subjectDTO.getStatus())) {
            subjectRepository.delete(SubjectMapper.mapDTOToEntity(subjectDTO));
            return;
        }
        throw new InvalidStatusException();
    }

    @Override
    public List<SubjectDTO> findAll() {
        return SubjectMapper.mapEntityListToDTOList(subjectRepository.findAllByOrderByStatusDescNameDesc());
    }

    @Override
    public List<SubjectDTO> findByName(String name ) {
        return SubjectMapper.mapEntityListToDTOList(subjectRepository.findByName(name));
    }



}
