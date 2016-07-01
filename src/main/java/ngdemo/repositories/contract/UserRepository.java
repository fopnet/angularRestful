package ngdemo.repositories.contract;

import ngdemo.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);

}
