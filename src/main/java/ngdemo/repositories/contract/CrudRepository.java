package ngdemo.repositories.contract;

import java.io.Serializable;

public interface CrudRepository<T, PK extends Serializable> extends Repository<T, PK> {

    T create(T t);

    T update(T t);

    T remove(T t);


}
