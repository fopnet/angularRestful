package ngdemo.repositories.contract;

import java.io.Serializable;

public interface CrudRepository<T, PK extends Serializable> extends Repository<T> {

    T create(T t);

    T update(T user);

    T remove(PK id);


}
