package ngdemo.repositories.impl.mock;

import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;
import ngdemo.domain.NullUser;
import ngdemo.domain.Profile;
import ngdemo.domain.ProfileType;
import ngdemo.domain.User;
import ngdemo.repositories.contract.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Repository;

import javax.management.RuntimeOperationsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import com.google.inject.Singleton;

//@Singleton
@Repository("userDAO")
public class UserMockRepositoryImpl extends GenericMockRepository<User> implements UserRepository {

    private final List<User> users;

    /** singleton instance */
    private static UserRepository INSTANCE;

    public static UserRepository getInstance(){
        if(INSTANCE == null){
            synchronized(UserMockRepositoryImpl.class){
                if(INSTANCE == null){
                    INSTANCE = new UserMockRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    private UserMockRepositoryImpl() {
        this.users = new ArrayList<User>();
        this.createUsers();
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
        return Collections.unmodifiableList(this.users);
    }

    @Override
    public User create(User user) {
        user.setId(getCurrentMaxId() + 1);
        user.setProfile(ProfileType.getById(user.getId()).toProfile());
        this.users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        User byId = this.getById(user.getId());
        byId.setFirstName(user.getFirstName());
        byId.setLastName(user.getLastName());
        byId.setProfile(ProfileType.getById(user.getId()).toProfile());
        byId.setPassword(user.getPassword());
        byId.setEmail(user.getEmail());
        return byId;
    }

    @Override
    public User remove(Long id) {
        User byId = this.getById(id);
        if (!this.users.remove(byId)) {
            throw new RuntimeOperationsException(null, "Could not found the item");
        }
        return byId;
    }

    @Override
    public int getNumberOfUsers() {
        return this.users.size();
    }

    private void createUsers() {
        int numberOfUsers = 10;
        Profile publc = ProfileType.PUBLIC.toProfile();
        Profile publisher = ProfileType.PUBLISHER.toProfile();

        for (long i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setFirstName("User" + (i + 1));
            user.setLastName("Surname" + (i + 1));
            user.setPassword(RandomStringUtils.randomAlphanumeric(10));
            user.setEmail("abc@example.com");
            user.setProfile( i % 2 == 0 ? publc : publisher) ;
            this.users.add(user);
        }
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
