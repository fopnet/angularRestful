package ngdemo.repositories.impl.mock;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.SubscriptionID;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.SubscriptionRepository;
import ngdemo.repositories.contract.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import javax.management.RuntimeOperationsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Singleton
//@Repository("subscriptionDAO")
public class SubscriptionMockRepositoryImpl
        extends GenericMockRepository<Subscription, SubscriptionID>
        implements SubscriptionRepository {

    private final List<Subscription> subscriptions;

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
        this.subscriptions = new ArrayList<>();

        this.createSubscriptions();
    }

    private List<Subscription> createSubscriptions() {
        List<Journal> journals = journalRepository.getAll();
        User usr = userRepository.getAll().iterator().next();
        for (int i = 0; i < journals.size()/2; i++) {
            this.subscriptions.add(new Subscription(journals.get(i), usr, new Date()));
        }
        return this.subscriptions;
    }

    public List<Subscription> getAll() {
        return subscriptions;
    }

    @Override
    public Subscription getById(SubscriptionID id) {
        return IterableUtils.find(getAll(), new Predicate<Subscription>() {
            @Override
            public boolean evaluate(Subscription o) {
                return o.getId().equals(id);
            }
        });
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
    public Subscription remove(Subscription subscription) {
        if (!this.subscriptions.remove(subscription)) {
            throw new RuntimeOperationsException(null, "Could not found the item");
        }
        return subscription;
    }

    @Override
    public Subscription create(Subscription subscription) {
        subscription.setDate(new Date());
        this.subscriptions.add(subscription);
        return subscription;
    }

    @Override
    public Subscription update(Subscription subscription) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

}
