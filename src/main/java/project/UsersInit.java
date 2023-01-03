package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.persistence.entity.UserEntity;
import project.persistence.repository.UserRepository;
import project.service.Roles;

@Component
public class UsersInit {

    private UserRepository userRepository;

    @Autowired
    public UsersInit(UserRepository userRepository) {
        this.userRepository = userRepository;

        UserEntity journalist = userRepository.findByUsernameAndPassword("dimosiografos", "dimosiografos");
        UserEntity supervisor = userRepository.findByUsernameAndPassword("epimelitis", "epimelitis");

        if (journalist == null) {
            userRepository.save(createUser("dimosiografos", "dimosiografos", Roles.JOURNALIST, "Plhres Onoma Dimosiografou"));
        }
        if (supervisor == null) {
            userRepository.save(createUser("epimelitis", "epimelitis", Roles.SUPERVISOR, "Plhres Onoma Epimelith"));
        }
    }


    private UserEntity createUser(String username, String password, String role, String fullName) {
        UserEntity user = new UserEntity();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        return user;
    }
}
