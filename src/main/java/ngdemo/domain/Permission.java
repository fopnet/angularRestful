package ngdemo.domain;


import com.google.common.base.Strings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Felipe on 28/06/2016.
 */
//@XmlRootElement
@Entity
@Table(name="PERMISSION")
@SequenceGenerator(name="INC_PERMISSION", sequenceName = "GEN_PERMISSION")
public class Permission implements Serializable {

    @Id
    @Column(name = "PERMISSIONID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INC_PERMISSION")
    private Long id;

    @Column(name = "NAME",length = 100)
    @NotNull
    @NotEmpty
    @Length(max = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @Transient
    private Set<Profile> profiles;

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

    public Set<Profile> getProfiles() {
        return Collections.unmodifiableSet( profiles );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission other  = (Permission) o;
        return new EqualsBuilder()
                .append(  Strings.nullToEmpty(getName()).toLowerCase(),
                          Strings.nullToEmpty(other.getName()).toLowerCase())
                .append(  getId(), other.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(Strings.nullToEmpty( getName() ).toLowerCase())
                .append( getId() )
                .toHashCode();
    }

}
