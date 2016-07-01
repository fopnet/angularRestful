package ngdemo.repositories.impl.hibernate;

import ngdemo.domain.User;
import ngdemo.repositories.contract.UserRepository;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Felipe on 30/06/2016.
 */
public class UserHibernateRepository extends AbstractCrudHibernateDAO<User,Long> implements UserRepository {

    @Autowired
    public UserHibernateRepository(final SessionFactory factory) {
        super.setSessionFactory(factory);
    }

    @Override
    public User getByEmail(String email) {
        Iterator<?> it = getHibernateTemplate()
                .findByCriteria(DetachedCriteria.forClass(User.class)
                        .add(Restrictions.eq("email", email)))
                .iterator();
        return it.hasNext() ? (User) it.next() : null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = (List<User>) getHibernateTemplate()
                .find("from User u inner join fetch u.profile p");
        return users;
    }
}
