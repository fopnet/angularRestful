package ngdemo.repositories.impl.mock;

import ngdemo.repositories.contract.Repository;

import java.io.Serializable;
import java.util.List;

public abstract class GenericMockRepository<T, PK extends Serializable> implements Repository<T, PK> {

    @Override
    public List<T> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T getById(PK id) {
        return (T) null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("getCount method needs to be implemented.");
    }

    public T remove (T t) {
        return null;
    }
}
