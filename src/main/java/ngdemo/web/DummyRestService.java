package ngdemo.web;

//import com.google.inject.Inject;

import ngdemo.domain.User;
import ngdemo.service.contract.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

//import javax.ws.rs.Path;

//@Path("/dummy")
@RestController
@RequestMapping("/web")
public class DummyRestService {
    private final DummyService dummyService;

    //@Inject
    @Autowired
    public DummyRestService(DummyService dummyService) {
        this.dummyService = dummyService;
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    @RequestMapping(value = "/dummy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public User getDefaultDummyInJSON() {
        return dummyService.getDefaultUser();
    }
}
