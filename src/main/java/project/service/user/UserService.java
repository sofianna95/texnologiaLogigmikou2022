package project.service.user;

import project.persistence.entity.UserEntity;

public interface UserService {

    UserEntity login(String username, String password);
}
