package ngdemo.repositories.impl.hibernate;

import ngdemo.repositories.contract.CrudRepository;

import java.io.Serializable;

/**
 * Created by Felipe on 30/06/2016.
 */
abstract class AbstractCrudHibernateDAO<T, PK extends Serializable>
        extends AbstractHibernateDAO<T, PK> implements CrudRepository<T, PK> {

    @Override
    public T create(T obj) {
        getHibernateTemplate().save (obj);
        return  obj;
    }

    @Override
    public T update(T obj) {
        getHibernateTemplate().update(obj);
        return obj;
    }

    @Override
    public T remove(T obj) {
        getHibernateTemplate().delete(obj);
        return obj;
    }

}
