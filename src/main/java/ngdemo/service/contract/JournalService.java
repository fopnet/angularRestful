package ngdemo.service.contract;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;

import java.util.List;

public interface JournalService {

    List<Journal> getAll();

    Journal getById(Long id);

    Journal create(Journal journal);

    Journal update(Journal journal);

    Journal remove(Long id);

    // This is a front-end funcionality
    //Journal readOnline(Journal journal);

}
