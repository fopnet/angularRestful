package ngdemo.domain;

import com.google.common.base.Strings;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Felipe on 28/06/2016.
 */
@XmlRootElement
@Entity
@Table(name="PROFILE")
@SequenceGenerator(name="INC_PROFILE", sequenceName = "GEN_PROFILE")
public class Profile {
    @Id
    @Column(name = "PROFILEID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INC_PROFILE")
    private Long id;

    @Column(name = "NAME",length = 100)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "PERMISSION_PROFILE",
            joinColumns = { @JoinColumn(name = "PROFILEID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "PERMISSIONID", nullable = false, updatable = false) })
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "profile")
    @Cascade (CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        Profile other  = (Profile) o;
        EqualsBuilder builder = new EqualsBuilder();
        EqualsBuilder append = builder.append(  Strings.nullToEmpty(getName()).toLowerCase(),
                                                Strings.nullToEmpty(other.getName()).toLowerCase());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(Strings.nullToEmpty( getName() ).toLowerCase());
        return builder.toHashCode();
    }

}
