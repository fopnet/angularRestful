package ngdemo.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Felipe on 28/06/2016.
 */
//@XmlRootElement
@Entity
@Table(name="SUBSCRIPTION")
public class Subscription implements  Comparable<Subscription> , Serializable {

    protected Subscription () {
        ///to hibernate use
    }

    public Subscription (Journal j , User user) {
        this.journal = j;
        this.subscriber = user;
    }

    public Subscription (Journal j , User user, Date publicationDate) {
        this(j, user);
        this.date = publicationDate;
    }

    @EmbeddedId
    private SubscriptionID id;

    @ManyToOne
    @JoinColumn(name="JOURNALID")
    @NotNull
    private Journal journal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USERID")
    @NotNull
    private User subscriber;

    @Column(name = "SUBSCRIPTION_DATE")
    private Date date;

    public SubscriptionID getId() {
        return id;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Subscription)) {
            return false;
        }
        Subscription other  = (Subscription) o;
        return new EqualsBuilder()
                .append( getId() , other.getId()  )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append (getId())
                .toHashCode();
    }

    @Override
    public int compareTo(Subscription o) {
        return getJournal().getSubject().compareTo( o.getJournal().getSubject() );
    }

}
