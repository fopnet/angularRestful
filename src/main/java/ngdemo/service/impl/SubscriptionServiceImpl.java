package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.SubscriptionRepository;
import ngdemo.service.contract.SubscriptionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//@Singleton
@Service("subscriptionService")
@org.springframework.transaction.annotation.Transactional
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
    public List<Subscription> getAllBySubscriber(User subscriber) {
        List<Subscription> subscriptions = subscriptionRepository.getAll();

        List<Journal> subscribedJournals = new ArrayList<Journal>();

        // get user subscriptions
        List<Subscription> userSubscription = ListUtils.select(subscriptions, new Predicate<Subscription>() {
            @Override
            public boolean evaluate(Subscription item) {
                subscribedJournals.add(item.getJournal());

                return item.getSubscriber().equals(subscriber);
            }
        });


       // Sort by journal subjects
        Collections.sort(userSubscription);

        // List all Journals
        List<Journal> notSubscribedJournals = new ArrayList<Journal>( journalRepository.getAll() );

        // Remove all journals already subscribed
//        CollectionUtils.removeAll(notSubscribedJournals, subscribedJournals);
        notSubscribedJournals.removeAll(subscribedJournals);

        // Transform journals not subscribed in possible  futures subscriptions
        List<Subscription> unSubscribedJournals = (List<Subscription>)  CollectionUtils.collect(notSubscribedJournals, new Transformer<Journal, Subscription>() {
            @Override
            public Subscription transform(Journal input) {
                return new Subscription(input, null);
            }
        });

        // Ordering by journal subject, the second list
        Collections.sort(unSubscribedJournals);

        // concatenating the two lists keeping the ordering
        userSubscription.addAll(unSubscribedJournals);

        return userSubscription;
    }

    @Override
    public Subscription subscribe(Subscription subscription, User subscriber) {
        subscription.setSubscriber(subscriber);
        subscription.setDate(new Date());
        return this.subscriptionRepository.create(subscription);
    }

    @Override
    public boolean unSubscribe(Long idJournal, User subscriber) {
        Subscription subscription = createSubscription(idJournal, subscriber);
        this.subscriptionRepository.remove(subscription);
        return true;
    }

    private Subscription createSubscription(Long idJournal, User subscriber) {
        Journal journal = journalRepository.getById(idJournal);
        return new Subscription(journal, subscriber, new Date());
    }

}
