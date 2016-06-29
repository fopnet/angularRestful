package ngdemo.service.contract;

import ngdemo.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(Long id);

    User createNewUser(User user);

    User update(User user);

    User remove(Long id);

    int getNumberOfUsers();

    User login(User user);
}
