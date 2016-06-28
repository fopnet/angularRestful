package ngdemo.repositories.impl.mock;

import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;
import ngdemo.domain.NullUser;
import ngdemo.domain.User;
import ngdemo.repositories.contract.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//import com.google.inject.Singleton;

//@Singleton
@Repository("userDAO")
public class UserMockRepositoryImpl extends GenericMockRepository<User> implements UserRepository {

    private List<User> users = new ArrayList<User>();

    public UserMockRepositoryImpl() {
        this.users = this.createUsers();
    }

    public User getById(Long id) {
        for (User u : this.users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return new NullUser();
    }

    public User getByEmail(String email) {
        email = Strings.nullToEmpty(email).trim();

        for (User u : this.users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return new NullUser();
    }

    @Override
    public User loadUserByEmail(String login) {
        return getByEmail(login);
    }

    public List<User> getAll() {
        return this.users;
    }

    @Override
    public User create(User user) {
        user.setId(getCurrentMaxId() + 1);
        this.users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        User byId = this.getById(user.getId());
        byId.setFirstName(user.getFirstName());
        byId.setLastName(user.getLastName());
        return byId;
    }

    @Override
    public User remove(Long id) {
        User byId = this.getById(id);
        this.users.remove(byId);
        return byId;
    }

    @Override
    public int getNumberOfUsers() {
        return this.users.size();
    }

    private List<User> createUsers() {
        int numberOfUsers = 10;
        for (long i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setFirstName("Foo" + (i + 1));
            user.setLastName("Bar" + (i + 1));
            this.users.add(user);
        }
        return this.users;
    }

    private Long getCurrentMaxId() {
        Ordering<User> ordering = new Ordering<User>() {
            @Override
            public int compare(User left, User right) {
                return Longs.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.users).getId();
    }


}