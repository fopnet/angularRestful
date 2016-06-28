package ngdemo.repositories.contract;

import ngdemo.domain.User;

public interface UserRepository extends Repository<User> {
    User create(User user);

    User update(User user);

    User remove(Long id);

    int getNumberOfUsers();

    User loadUserByEmail(String username);

    User getByEmail(String email);

}
