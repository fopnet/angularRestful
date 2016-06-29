package ngdemo.service.contract;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getAllBySubscriber(User subscriber, boolean isOnlySubscribed) ;

    Subscription subscribe(Journal journal, User subscriber);

    boolean unSubscribe(Subscription subscription);

    // This is a front-end funcionality
    //Journal readOnline(Journal journal);

}
