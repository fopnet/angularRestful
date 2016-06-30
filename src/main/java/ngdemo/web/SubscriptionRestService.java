package ngdemo.web;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import ngdemo.service.contract.JournalService;
import ngdemo.service.contract.SubscriptionService;
import ngdemo.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/web/subscriptions")
public class SubscriptionRestService {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

    public SubscriptionRestService() {
    }

    /**
     *  1.list all journals subscribed by logged user, order by subject
     *  2.list all journals not subscribed yet by logged user order by subject
     *  3.Concat both lists
     */
    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON)
    public @ResponseBody List<Subscription> getAllUserSubscriptionsInJSON(HttpServletRequest request) {
        User subscriber =  getLoggedUser(request);
        return subscriptionService.getAllBySubscriber(subscriber);
    }

    @RequestMapping(method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON,
                    consumes = MediaType.APPLICATION_JSON)
    public Subscription subscribe(@RequestBody Subscription subscription,
                                  HttpServletRequest request) {

        return subscriptionService.subscribe(subscription, getLoggedUser(request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public boolean unSubscribe(@PathVariable("id") Long idJournal,
                                HttpServletRequest request) {
        return subscriptionService.unSubscribe(idJournal, getLoggedUser(request));
    }

    private User getLoggedUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("logged");
        if  (user == null) {
            //TODO remove after login artefact to be done!
            user = userService.getById(1L);
        }
        return user;
    }

}
