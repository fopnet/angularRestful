package ngdemo.service.contract;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getAllBySubscriber(User subscriber) ;

    Subscription subscribe(Subscription subscription, User subscriber);

    boolean unSubscribe(Long idJournal, User loggedUser);

    // Initially, This is a front-end funcionality
    //Journal readOnline(Journal journal);

}
