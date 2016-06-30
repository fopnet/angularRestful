package ngdemo.web;

import ngdemo.domain.Journal;
import ngdemo.service.contract.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/web/journals")
public class JournalRestService {

    public static final String UPLOAD_PATH = "/uploads/";
    private final JournalService journalService;

    @Autowired
    public JournalRestService(JournalService journalService) {
        this.journalService = journalService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Journal> getAllJournalsInJSON() {
        return journalService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Journal getJournalById(@PathVariable("id") Long id) {
        return journalService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON,
                    consumes = MediaType.MULTIPART_FORM_DATA )
    public Journal create( @RequestPart("journal") Journal  journal,
                           @RequestPart("file") MultipartFile file ,
                           HttpServletRequest  request) {
        journal.setFileName(file.getOriginalFilename());
        return journalService.create(journal, file, request.getServletContext().getRealPath(UPLOAD_PATH));
    }

    @RequestMapping( value = "/file", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON,
                    consumes = MediaType.MULTIPART_FORM_DATA )
    public Journal update(@RequestPart(value ="journal") Journal journal,
                          @RequestPart(value= "file") MultipartFile file,
                          HttpServletRequest  request) {
        return journalService.update(journal, file, request.getServletContext().getRealPath(UPLOAD_PATH));
    }

    @RequestMapping( value = "/nofile", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON,
                    consumes = MediaType.MULTIPART_FORM_DATA )
    public Journal updateNoFile(@RequestPart(value ="journal") Journal journal,
                                HttpServletRequest  request) {
        return journalService.update(journal, null, request.getServletContext().getRealPath(UPLOAD_PATH));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public Journal remove(@PathVariable("id") Long id) {
        return journalService.remove(id);
    }

}
