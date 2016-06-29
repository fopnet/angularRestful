package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.SubscriptionRepository;
import ngdemo.service.contract.SubscriptionService;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Singleton
@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final JournalRepository journalRepository;

//    @Inject
    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, JournalRepository journalRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.journalRepository = journalRepository;
    }

    @Override
    public List<Subscription> getAllBySubscriber(User subscriber, boolean isOnlySubscribed) {
        List<Subscription> subscriptions = subscriptionRepository.getAll();
        Predicate<Subscription> predicate = null;

        if (isOnlySubscribed) {

            predicate = new Predicate<Subscription>() {
                @Override
                public boolean evaluate(Subscription item) {
                    return item.equals(subscriber);
                }
            };

        } else {

            predicate = new Predicate<Subscription>() {
                @Override
                public boolean evaluate(Subscription item) {
                    return  item.getSubscriber() == null || item.equals(subscriber) ;
                }
            };

        }
        return ListUtils.select(subscriptions, predicate);
    }

    @Override
    public Subscription subscribe(Journal journal, User subscriber) {
        return this.subscriptionRepository.create(new Subscription(journal, subscriber));
    }

    @Override
    public boolean unSubscribe(Subscription subscription) {
        return this.subscriptionRepository.remove(subscription);
    }

}
