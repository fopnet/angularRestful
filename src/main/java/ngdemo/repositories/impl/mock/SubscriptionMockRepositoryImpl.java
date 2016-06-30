package ngdemo.repositories.impl.mock;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.SubscriptionRepository;
import ngdemo.repositories.contract.UserRepository;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeOperationsException;
import java.util.*;

//@Singleton
@Repository("subscriptionDAO")
public class SubscriptionMockRepositoryImpl implements SubscriptionRepository {

    private final Set<Subscription> subscriptions;

    private UserRepository userRepository;
    private JournalRepository journalRepository;

    /** singleton instance */
    private static SubscriptionRepository INSTANCE;

    public static SubscriptionRepository getInstance(final UserRepository userRepository, final JournalRepository journalRepository){
        if(INSTANCE == null){
            synchronized(SubscriptionMockRepositoryImpl.class){
                if(INSTANCE == null){
                    INSTANCE = new SubscriptionMockRepositoryImpl(userRepository, journalRepository);
                }
            }
        }
        return INSTANCE;
    }

    private SubscriptionMockRepositoryImpl(final UserRepository userRepository, final JournalRepository journalRepository) {
        this.userRepository = userRepository;
        this.journalRepository = journalRepository;
        this.subscriptions = new HashSet<Subscription>();

        this.createSubscriptions();
    }

    private Set<Subscription> createSubscriptions() {
        List<Journal> journals = journalRepository.getAll();
        User usr = userRepository.getAll().iterator().next();
        for (int i = 0; i < journals.size()/2; i++) {
            this.subscriptions.add(new Subscription(journals.get(i), usr, new Date()));
        }
        return this.subscriptions;
    }

    @Override
    public Set<Subscription> getAll() {
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
