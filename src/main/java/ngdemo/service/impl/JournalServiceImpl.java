package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import ngdemo.domain.Journal;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.service.contract.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Singleton
@Service("journalService")
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;

//    @Inject
    @Autowired
    public JournalServiceImpl(JournalRepository repository) {
        this.journalRepository = repository;
    }

    @Override
    public List<Journal> getAll() {
        return this.journalRepository.getAll();
    }

    @Override
    public Journal getById(Long id) {
        return this.journalRepository.getById(id);
    }

    @Override
    public Journal create(Journal user) {
        Journal u = this.journalRepository.create(user);
        return u;
    }

    @Override
    public Journal update(Journal user) {
        return this.journalRepository.update(user);
    }

    @Override
    public Journal remove(Long id) {
        return this.journalRepository.remove(id);
    }


}
