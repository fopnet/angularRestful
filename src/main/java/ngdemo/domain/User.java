package ngdemo.domain;

import com.google.common.base.Strings;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity
@Table(name="USER")
@SequenceGenerator(name="INC_USER", sequenceName = "GEN_USER")
public class User implements Serializable  {

    @Id
    @Column(name = "USERID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INC_USER")
    private Long id;

    @Column(name = "FIRSTNAME")
    @NotEmpty
    @Length(max = 100)
    private String firstName;

    @Column(name = "LASTNAME")
    @NotEmpty
    @Length(max = 100)
    private String lastName;

    @Column(name = "PASSWORD")
    @NotEmpty
    @Length(max = 10)
    private String password;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "EMAIL",length = 100)
    @NotNull
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name="PERMISSION_PROFILE",
            joinColumns={@JoinColumn(name="USERID", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="PROFILEID", referencedColumnName="id")})
    private Profile profile;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
    @JoinTable(name="SUBSCRIPTION",
            joinColumns={@JoinColumn(name="USERID", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="SUBSCRIPTIONID", referencedColumnName="id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Subscription> subscriptions;

    public User() {
        this.subscriptions = new HashSet<Subscription>();
    }

    /*********************************************************************
     * Getters and setters
     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        if (profile == null) {
            profile = new Profile();
        }
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /*********************************************************************
     * Methods
     */
    public boolean hasProfile(String permissionName) {
        if (profile != null) {
            profile.getName().compareToIgnoreCase(permissionName);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User other  = (User) o;
        return new EqualsBuilder()
                    .append(Strings.nullToEmpty(getEmail()).toLowerCase(),
                            Strings.nullToEmpty(other.getEmail()).toLowerCase())
                    .append(getId(),
                            other.getId())
                    .append(Strings.nullToEmpty(getFirstName()),
                            Strings.nullToEmpty(other.getFirstName()))
                    .append(Strings.nullToEmpty(getLastName()),
                            Strings.nullToEmpty(other.getLastName()))
                    .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(Strings.nullToEmpty( getEmail() ).toLowerCase())
                .append( getId() )
                .append( getFirstName() )
                .append( getLastName() )
                .toHashCode();
    }

}
