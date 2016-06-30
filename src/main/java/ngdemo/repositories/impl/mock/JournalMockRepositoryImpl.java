package ngdemo.repositories.impl.mock;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;
import ngdemo.domain.Journal;
import ngdemo.domain.User;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.UserRepository;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeOperationsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Singleton
@Repository("journalDAO")
public class JournalMockRepositoryImpl extends GenericMockRepository<Journal> implements JournalRepository {

    private List<Journal> journals = new ArrayList<Journal>();

    private UserRepository userRepository;

    public JournalMockRepositoryImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
        this.journals = this.createJournals();
    }

    public Journal getById(Long id) {
        for (Journal item : this.journals) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<Journal> getAll() {
        return this.journals;
    }

    @Override
    public Journal create(Journal Journal) {
        Journal.setId(getCurrentMaxId() + 1);
        this.journals.add(Journal);
        return Journal;
    }

    @Override
    public Journal update(Journal journal) {
        Journal byId = this.getById(journal.getId());
        byId.setFileName(journal.getFileName());
        byId.setPublisher(journal.getPublisher());
        byId.setSubject(journal.getSubject());
        return byId;
    }

    @Override
    public Journal remove(Long id) {
        Journal byId = this.getById(id);
        if (!this.journals.remove(byId)) {
            throw new RuntimeOperationsException(null, "Could not found the item");
        }
        return byId;
    }


    /******************************************************************************
     * Temp implementations
     * @return
     */
    private List<Journal> createJournals() {
        int numberOfJournals = 4;
        List<User> users =   userRepository.getAll();

        for (long i = 0; i < numberOfJournals; i++) {
            int randomIdx = new Random().nextInt((users.size()-1) + 1) ;

            Journal journal = new Journal();
            journal.setId(i + 1);
            journal.setSubject("Subject " + (i + 1));
            journal.setFileName("Path " + (i + 1));
            journal.setPublisher(users.get(randomIdx));
            this.journals.add(journal);
        }
        return this.journals;
    }

    private Long getCurrentMaxId() {
        Ordering<Journal> ordering = new Ordering<Journal>() {
            @Override
            public int compare(Journal left, Journal right) {
                return Longs.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.journals).getId();
    }


}
