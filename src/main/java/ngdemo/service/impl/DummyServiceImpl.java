package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;
import ngdemo.domain.User;
import ngdemo.repositories.contract.DummyRepository;
import ngdemo.service.contract.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Singleton
@Service("dummyService")
public class DummyServiceImpl implements DummyService {

    private final DummyRepository dummyRepository;

    //@Inject
    @Autowired
    public DummyServiceImpl(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public User getDefaultUser() {
        Object defaultUser = this.dummyRepository.getDefaultUser();
        return this.dummyRepository.getDefaultUser();
    }

}
