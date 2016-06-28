package ngdemo.repositories.contract;

import ngdemo.domain.User;

import java.io.Serializable;
import java.util.List;

public interface CrudRepository<T, PK extends Serializable> extends Repository<T> {

    T create(T t);

    T update(T user);

    T remove(PK id);


}
