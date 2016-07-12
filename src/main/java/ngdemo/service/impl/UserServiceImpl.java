package ngdemo.service.impl;

//import com.google.inject.Inject;
//import com.google.inject.Singleton;

import com.google.common.base.Strings;
import ngdemo.domain.ProfileType;
import ngdemo.domain.User;
import ngdemo.repositories.contract.UserRepository;
import ngdemo.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Singleton
@Service("userService")
//@javax.transaction.Transactional
@org.springframework.transaction.annotation.Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

//    @Inject
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.getById(id);
    }

    @Override
    public User createNewUser(User user) {
        User u = this.userRepository.create(user);
        return u;
    }

    @Override
    public User update(User user) {
        return this.userRepository.update(user);
    }

    @Override
    public User remove(Long id) {
        return this.userRepository.remove( getById(id) );
    }

    @Override
    public int getNumberOfUsers() {
        return this.userRepository.getCount();
    }

    @Override
    public Authentication login(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);

        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("User or password do not match");
        }

//        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) userDetails;

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
        auth.setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.getByEmail(username);
        final boolean isActive = true;
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return buildUserFromUserEntity(user);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(ProfileType.getRoleName(user.getProfile())));
        return authorities;
    }

    private org.springframework.security.core.userdetails.User buildUserFromUserEntity(User userEntity) {
        // convert model user to spring security user
        String username = userEntity.getFirstName() + " " + Strings.nullToEmpty(userEntity.getLastName());
        String password = userEntity.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = getGrantedAuthorities(userEntity);

        org.springframework.security.core.userdetails.User  springUser =
                new org.springframework.security.core.userdetails.User(username, password, enabled,
                        accountNonExpired, credentialsNonExpired, accountNonLocked,
                        authorities);
        return springUser;
    }




}
