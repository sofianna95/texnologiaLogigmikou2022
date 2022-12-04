package project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.api.ApplicationAPI;
import project.dto.SubjectDTO;
import project.service.shoppingCard.SubjectService;

@RestController
public class ApplicationController implements ApplicationAPI {

    private final SubjectService subjectService;


    @Autowired
    public ApplicationController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public ResponseEntity<SubjectDTO> create(SubjectDTO shoppingCardDTO, String username, String password) {
        SubjectDTO shoppingCardDTO1 = subjectService.create(shoppingCardDTO);
        return new ResponseEntity<>(shoppingCardDTO1, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SubjectDTO> update(Long id, SubjectDTO shoppingCardDTO, String username, String password) {

        return new ResponseEntity<>(subjectService.update(shoppingCardDTO, id), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> submit(Long id, String username, String password) {
        subjectService.approve(id);
        return new ResponseEntity<>("Subject approved", HttpStatus.OK);
    }

    public ResponseEntity<String> reject(Long id, String username, String password) {
        subjectService.reject(id);
        return new ResponseEntity<>("Subject rejected", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SubjectDTO> findById(Long id, String username, String password) {
        return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SubjectDTO>> findAll(String username, String password) {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SubjectDTO>> findByName(String name, String username, String password) {
        return new ResponseEntity<>(subjectService.findByName(name), HttpStatus.OK);
    }

    //    @Override
    //    public ResponseEntity<String> delete(long id, String loggedInUserName, String token, String customerUserName) {
    //        String loggedInUserRole = authenticationService.verifyToken(token, loggedInUserName);
    //        authenticationService.verifyRole(loggedInUserRole, customerUserName, loggedInUserName, Constants.EMPLOYEE);
    //        userService.findByUserName(customerUserName);
    //        subjectService.delete(id);
    //
    //
    //        return new ResponseEntity<>("Shopping Card deleted", HttpStatus.OK
    //
    //        );
    //    }
    //

    //
    //    @Override
    //    public ResponseEntity<List<ShoppingCardDTO>> findByUserName(String customerUserName, String loggedInUserName, String token) {
    //        String loggedInUserRole = authenticationService.verifyToken(token, loggedInUserName);
    //        authenticationService.verifyRole(loggedInUserRole, customerUserName, loggedInUserName, Constants.EMPLOYEE);
    //        return new ResponseEntity<>(subjectService.findByUserName(customerUserName), HttpStatus.OK);
    //    }
    //



}
