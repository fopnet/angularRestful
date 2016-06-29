package ngdemo.repositories.impl.mock;

import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.service.contract.SubscriptionRepository;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Singleton
@Repository("subscriptionDAO")
public class SubscriptionMockRepositoryImpl implements SubscriptionRepository {

    private List<Subscription> subscriptions = new ArrayList<Subscription>();

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionMockRepositoryImpl() {
    }

    @Override
    public List<Subscription> getAllBySubscriber(User subscriber) {
        return ListUtils.select(subscriptions, new Predicate<Subscription>() {
            @Override
            public boolean evaluate(Subscription o) {
                return o.equals(subscriber);
            }
        });
    }

    @Override
    public Subscription create(Subscription subscription) {
        subscription.setDate(new Date());
        this.subscriptions.add(subscription);
        return subscription;
    }

    @Override
    public boolean remove(Subscription subscription) {
        return this.subscriptions.remove(subscription);
    }

}
