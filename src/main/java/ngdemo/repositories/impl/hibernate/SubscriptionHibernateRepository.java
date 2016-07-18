package ngdemo.repositories.impl.hibernate;

import ngdemo.domain.Subscription;
import ngdemo.domain.SubscriptionID;
import ngdemo.domain.User;
import ngdemo.repositories.contract.SubscriptionRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 01/07/2016.
 */
public class SubscriptionHibernateRepository
        extends AbstractCrudHibernateDAO<Subscription, SubscriptionID>
        implements SubscriptionRepository {

    @Autowired
    public SubscriptionHibernateRepository(final SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Subscription> getAllBySubscriber(User subscriber) {
        List subbscritions = getHibernateTemplate()
                .findByCriteria(DetachedCriteria.forClass(getWildcard())
                                .add(Restrictions.eq("subscriber", subscriber)));
        return Collections.checkedList(subbscritions, Subscription.class);
    }

}
