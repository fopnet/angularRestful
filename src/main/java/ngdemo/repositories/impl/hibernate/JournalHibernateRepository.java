package ngdemo.repositories.impl.hibernate;

import ngdemo.domain.Journal;
import ngdemo.repositories.contract.JournalRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Felipe on 30/06/2016.
 */
public class JournalHibernateRepository extends AbstractCrudHibernateDAO<Journal,Long> implements JournalRepository {

    @Autowired
    public JournalHibernateRepository(final SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


}
