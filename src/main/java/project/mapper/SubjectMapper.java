package project.mapper;

import java.util.ArrayList;
import java.util.List;
import project.dto.SubjectDTO;
import project.persistence.entity.SubjectEntity;

public class SubjectMapper {


    public static SubjectDTO mapEntityToDTO(SubjectEntity subjectEntity) {
        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId(subjectEntity.getId());
        subjectDTO.setCreatedDate(subjectEntity.getCreatedDate());
        subjectDTO.setName(subjectEntity.getName());
        subjectDTO.setParentSubject(subjectEntity.getParentSubject());
        subjectDTO.setChildSubjects(subjectEntity.getChildSubjects());
        subjectDTO.setStatus(subjectEntity.getStatus());

        return subjectDTO;
    }

    public static SubjectEntity mapDTOToEntity(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = new SubjectEntity();

        subjectEntity.setId(subjectDTO.getId());
        subjectEntity.setCreatedDate(subjectDTO.getCreatedDate());
        subjectEntity.setName(subjectDTO.getName());
        subjectEntity.setParentSubject(subjectDTO.getParentSubject());
        subjectEntity.setChildSubjects(subjectDTO.getChildSubjects());
        subjectEntity.setStatus(subjectDTO.getStatus());

        return subjectEntity;
    }

    public static List<SubjectDTO> mapEntityListToDTOList(List<SubjectEntity> subjectEntityList) {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (SubjectEntity cardEntity : subjectEntityList) {
            subjectDTOS.add(mapEntityToDTO(cardEntity));
        }
        return subjectDTOS;
    }

    public static List<SubjectEntity> mapDTOListToEntityList(List<SubjectDTO> subjectDTOList) {
        List<SubjectEntity> shoppingCardDTOs = new ArrayList<>();
        for (SubjectDTO cardEntity : subjectDTOList) {
            shoppingCardDTOs.add(mapDTOToEntity(cardEntity));
        }
        return shoppingCardDTOs;
    }
}
