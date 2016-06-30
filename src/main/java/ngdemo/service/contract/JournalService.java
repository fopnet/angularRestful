package ngdemo.service.contract;

import ngdemo.domain.Journal;
import ngdemo.domain.Subscription;
import ngdemo.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JournalService {

    List<Journal> getAll();

    Journal getById(Long id);

    Journal create(Journal journal,  MultipartFile file, String basePath);

    Journal update(Journal journal, MultipartFile file, String basePath);

    Journal remove(Long id);

    // This is a front-end funcionality
    //Journal readOnline(Journal journal);

}
