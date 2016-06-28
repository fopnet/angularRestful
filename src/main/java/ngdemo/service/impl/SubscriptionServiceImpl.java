package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.service.contract.JournalService;
import ngdemo.service.contract.SubscriptionRepository;
import ngdemo.service.contract.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Singleton
@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

//    @Inject
    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getAllBySubscriber(User subscriber) {
        return null;
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
