package ngdemo.repositories.impl.hibernate;

import ngdemo.repositories.contract.Repository;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Felipe on 30/06/2016.
 */
abstract class AbstractHibernateDAO<T, PK extends Serializable>
        extends org.springframework.orm.hibernate4.support.HibernateDaoSupport implements Repository<T, PK> {


//    protected Class<?> getWildcard() {
//        return TypeUtils.wrap(getClass()).getClass();
//    }

    DetachedCriteria getDetachedCriteria() {
        return DetachedCriteria.forClass(getWildcard());
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getWildcard(){
        if (getClass().getGenericSuperclass() instanceof ParameterizedType){
            return (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }

        Class<?> clazz = getClass();
        while(!clazz.getSuperclass().equals(AbstractHibernateDAO.class)) {
            clazz = clazz.getSuperclass();
        }
        Type[] typeArguments = getTypeArguments(clazz);
        return (Class<T>) typeArguments[0];
    }

    private Type[] getTypeArguments(Class<?> clazz) {
        return ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments();
    }


    @Override
    public List<T> getAll() {
        List<T> result = getHibernateTemplate().loadAll(getWildcard());
        return result;
    }

    @Override
    public T getById(PK id) {
        return getHibernateTemplate().get(getWildcard(), id);
    }

    @Override
    public int getCount() {
        return (int) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createCriteria(getWildcard())
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

 /*   @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate hibernateTemplate = super.createHibernateTemplate(sessionFactory);
        hibernateTemplate.getSessionFactory().openSession().setFlushMode(FlushMode.AUTO);
        return hibernateTemplate;
    }*/
}
