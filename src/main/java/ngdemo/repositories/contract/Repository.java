package ngdemo.repositories.contract;

import java.io.Serializable;
import java.util.List;

public interface Repository<T, PK extends Serializable> {

    List<T> getAll();

    T getById(PK id);

    int getCount();
}
