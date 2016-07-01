package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import ngdemo.domain.Journal;
import ngdemo.repositories.contract.JournalRepository;
import ngdemo.repositories.contract.UserRepository;
import ngdemo.service.contract.JournalService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

//@Singleton
@Service("journalService")
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;

    @Autowired
    private UserRepository userRepository;

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
    public Journal create(Journal journal, MultipartFile file, String basePath) {
        try {
            saveFileOnDisk(journal, file, basePath);

            journal = this.journalRepository.create(journal);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return journal;
    }

    @Override
    public Journal update(Journal journal, MultipartFile newFile, String basePath) {
        try {
            if  (newFile != null) {
                Journal oldData = journalRepository.getById(journal.getId());

                Path oldFile = Paths.get(FilenameUtils.concat(basePath, oldData.getFileName()));

                Files.deleteIfExists(oldFile);

                ///Save a new newFile
                Path newPath = Paths.get(FilenameUtils.concat(basePath, journal.getFileName()));

                saveFileOnDisk(journal, newFile, basePath);
            }

            this.journalRepository.update(journal);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return journal;

    }

    @Override
    public Journal remove(Long id) {
        Journal removed = getById(id);
        return this.journalRepository.remove(removed);
    }

    private void saveFileOnDisk(Journal journal, MultipartFile file, String basePath) throws IOException {
        //TODO Remove after build login artefact
        journal.setPublisher(userRepository.getAll().iterator().next());
        journal.setFileName(file.getOriginalFilename());

        Files.createDirectories( Paths.get(basePath) );

        Path target = Paths.get(FilenameUtils.concat(basePath, file.getOriginalFilename()));

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    }

}
