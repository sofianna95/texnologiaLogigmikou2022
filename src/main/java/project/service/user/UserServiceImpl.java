package project.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.errorhandling.exception.InvalidCredentialsException;
import project.persistence.entity.UserEntity;
import project.persistence.repository.UserRepository;
import project.service.Roles;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity login(String username, String password) {
        if(username == null || password == null){
            return createVisitor();
        }
        UserEntity user = userRepository.findByUsernameAndPassword(username, password);

        if(user == null){
            throw new InvalidCredentialsException();
        }

        return user;
    }

    private UserEntity createVisitor(){
        UserEntity visitor = new UserEntity();
        visitor.setRole(Roles.VISITOR);
        visitor.setFullName("");

        return  visitor;
    }
}
