package ngdemo.web;

//import com.google.inject.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import ngdemo.domain.User;
import ngdemo.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

//@Path("/users")
@RestController
@RequestMapping("/web/users")
public class UserRestService {

    private final UserService userService;

//    @Inject
    @Autowired
    public UserRestService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/numberOfUsers",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public int getNumberOfUsers() {
        int total = userService.getNumberOfUsers();
        return  total;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<User> getAllUsersInJSON() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public User getUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public User create(@RequestBody User user) {
        return userService.createNewUser(user);
    }

//    @PUT
//    @Path("{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

//    @DELETE
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public User remove(@PathVariable("id") int id) {
        return userService.remove(id);
    }
}
