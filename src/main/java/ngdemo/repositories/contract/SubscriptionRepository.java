package ngdemo.repositories.contract;

import ngdemo.domain.Subscription;
import ngdemo.domain.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Felipe on 28/06/2016.
 */
public interface SubscriptionRepository {

    Set<Subscription> getAll() ;

    List<Subscription> getAllBySubscriber(User subscriber) ;

    Subscription create(Subscription subscription);

    boolean remove(Subscription subscription);
}
