package ngdemo.domain;


import com.google.common.base.Strings;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Felipe on 28/06/2016.
 */
@XmlRootElement
@Entity
@Table(name="JOURNAL")
@SequenceGenerator(name="INC_JOURNAL", sequenceName = "GEN_JOURNAL")
public class Journal {
    @Id
    @Column(name = "JOURNALID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INC_JOURNAL")
    private Long id;

    @Column(name = "SUBJECT",length = 100)
    @NotNull
    @NotEmpty
    @Length(max = 100)
    private String subject;

    @Column(name = "FILEPATH",length = 400)
    @NotNull
    @NotEmpty
    @Length(max = 100)
    private String fileName;

    @ManyToOne
    @JoinColumn(name="USERID")
    @NotNull
    private User publisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Journal)) {
            return false;
        }
        Journal other  = (Journal) o;
        return new EqualsBuilder()
                .append(  Strings.nullToEmpty(getFileName()).toLowerCase(),
                          Strings.nullToEmpty(other.getFileName()).toLowerCase())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(Strings.nullToEmpty( getFileName() ).toLowerCase())
                .toHashCode();
    }

}
