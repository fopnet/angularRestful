package ngdemo.web;

//import com.google.inject.Inject;

import ngdemo.domain.User;
import ngdemo.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//    @Secured("ROLE_PUBLISHER")
//    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public Long getNumberOfUsers() {
        Long total = userService.getNumberOfUsers();
        return  total;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
//    @Secured("ROLE_PUBLISHER")
//    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public List<User> getAllUsersInJSON() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
//    @Secured("ROLE_PUBLISHER")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON,
                    consumes = MediaType.APPLICATION_JSON)
    public User create(@RequestBody User user) {
        return userService.createNewUser(user);
    }

//    @PUT
//    @Path("{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
//    @Secured("ROLE_PUBLISHER")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

//    @DELETE
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
//    @Secured("ROLE_PUBLISHER")
    public User remove(@PathVariable("id") Long id) {
        return userService.remove(id);
    }

    @RequestMapping(value = "/authenticate",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON)
    public Map<String, Object> login(@RequestParam("username") String email,
                                    @RequestParam("password") String pwd,
                                    HttpServletRequest request) {
        Authentication auth = userService.login(email, pwd);

        Map<String, Object> viewModel = new LinkedHashMap<String, Object>();
        viewModel.put("username", auth.getName());
        viewModel.put("authdata",  request.getHeader("Authorization"));
        viewModel.put("roles", AuthorityUtils.authorityListToSet(auth.getAuthorities()));

        return viewModel;
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public boolean logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return true;
    }

/*    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }*/

}
