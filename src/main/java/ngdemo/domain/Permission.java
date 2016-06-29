package ngdemo.domain;


import com.google.common.base.Strings;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
@Table(name="PERMISSION")
@SequenceGenerator(name="INC_PERMISSION", sequenceName = "GEN_PERMISSION")
public class Permission {

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
        if (!(o instanceof User)) {
            return false;
        }
        Permission other  = (Permission) o;
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
