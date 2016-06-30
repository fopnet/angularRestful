package ngdemo.repositories.impl.mock;

import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.SubscriptionRepository;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeOperationsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Singleton
@Repository("subscriptionDAO")
public class SubscriptionMockRepositoryImpl implements SubscriptionRepository {

    private List<Subscription> subscriptions = new ArrayList<Subscription>();

    public SubscriptionMockRepositoryImpl() {
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptions;
    }

    @Override
    public List<Subscription> getAllBySubscriber(User subscriber) {
        return ListUtils.select(getAll(), new Predicate<Subscription>() {
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
        if (!this.subscriptions.remove(subscription)) {
            throw new RuntimeOperationsException(null, "Could not found the item");
        }
        return true;
    }

}
