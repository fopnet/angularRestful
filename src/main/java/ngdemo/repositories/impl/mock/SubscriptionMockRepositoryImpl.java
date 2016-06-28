package ngdemo.repositories.impl.mock;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.SubscriptionRepository;
import ngdemo.service.contract.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Singleton
@Repository("subscriptionDAO")
public class SubscriptionMockRepositoryImpl implements SubscriptionRepository {

    private List<Subscription> subscriptions = new ArrayList<Subscription>();

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionMockRepositoryImpl() {
        this.subscriptions = this.createSubscriptions();
    }

    public Subscription getById(Long id) {
        for (Subscription item : this.subscriptions) {
            if (item.equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<Subscription> getAll() {
        return this.subscriptions;
    }

    @Override
    public Subscription create(Subscription Subscription) {
        Subscription.setId(getCurrentMaxId() + 1);
        this.subscriptions.add(Subscription);
        return Subscription;
    }

    @Override
    public Subscription update(Subscription journal) {
        Subscription byId = this.getById(journal.getId());
        byId.setFilePath(journal.getFilePath());
        byId.setPublisher(journal.getPublisher());
        byId.setSubject(journal.getSubject());
        return byId;
    }

    @Override
    public Subscription remove(Long id) {
        Subscription byId = this.getById(id);
        this.subscriptions.remove(byId);
        return byId;
    }


    /******************************************************************************
     * Temp implementations
     * @return
     */
    private List<Subscription> createSubscriptions() {
        int numberOfSubscriptions = 10;
        List<User> users =   userRepository.getAll();

        for (long i = 0; i < numberOfSubscriptions; i++) {
            int randomIdx = new Random().nextInt((users.size()-1) + 1) + 1;

            Subscription journal = new Subscription();
            journal.setId(i + 1);
            journal.setSubject("Subject " + (i + 1));
            journal.setFilePath("Path " + (i + 1));
            journal.setPublisher(users.get(randomIdx));
            this.subscriptions.add(journal);
        }
        return this.subscriptions;
    }

    private Long getCurrentMaxId() {
        Ordering<Subscription> ordering = new Ordering<Subscription>() {
            @Override
            public int compare(Subscription left, Subscription right) {
                return Longs.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.subscriptions).getId();
    }


}
