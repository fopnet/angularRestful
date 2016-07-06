package ngdemo.infrastructure;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;


/**
 * Created by Felipe on 01/07/2016.
 */
public class AutoFlushOpenSessionInViewFilter extends org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {

    @Override
    protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        try {
            Session session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.AUTO); // This line changes the default behavior
            session.setDefaultReadOnly(false);
            return session;
        } catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
        }
    }

}