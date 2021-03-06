package ngdemo.domain;

import com.google.common.base.Strings;
import ngdemo.infrastructure.json.GsonExclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Felipe on 28/06/2016.
 */
@Entity
@Table(name="PROFILE")
@SequenceGenerator(name="INC_PROFILE", sequenceName = "GEN_PROFILE")
public class Profile implements Serializable {
    @Id
    @Column(name = "PROFILEID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INC_PROFILE")
    private Long id;

    @Column(name = "NAME",length = 100)
    @NotNull
    @NotEmpty
    @Length(max = 100)
    private String name;

    @ManyToMany(cascade= javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="PERMISSION_PROFILE",
            joinColumns={@JoinColumn(name="PROFILEID", nullable = false, updatable = false)},
            inverseJoinColumns={@JoinColumn(name="PERMISSIONID", nullable = false, updatable = false)})
    @Fetch(value = FetchMode.SUBSELECT)
    @GsonExclude
    @Transient
    private Set<Permission> permissions;

    public Profile(Long id, String name) {
        this();
        this.setId(id);
        this.setName(name);
    }

    public Profile() {

    }

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

    public Set<Permission> getPermissions() {
        if  (permissions == null)
            permissions = new HashSet<Permission>();
        return Collections.unmodifiableSet(permissions);
    }

    protected void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Profile)) {
            return false;
        }
        Profile other  = (Profile) o;
        return new EqualsBuilder()
                .append(  Strings.nullToEmpty(getName()).toLowerCase(),
                          Strings.nullToEmpty(other.getName()).toLowerCase())
                .append(  getId(),
                          other.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(Strings.nullToEmpty( getName() ).toLowerCase())
                .append(getId())
                .toHashCode();
    }

}
