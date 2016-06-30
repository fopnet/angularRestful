package ngdemo.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Felipe on 28/06/2016.
 */
@XmlRootElement
@Entity
@Table(name="SUBSCRIPTION")
public class Subscription {

    protected Subscription () {
        ///to hibernate use
    }

    public Subscription (Journal j , User user) {
        this.journal = j;
        this.subscriber = user;
    }

    @ManyToOne
    @JoinColumn(name="JOURNALID")
    @NotNull
    private Journal journal;

    @ManyToOne
    @JoinColumn(name="USERID")
    @NotNull
    private User subscriber;

    @Column(name = "date")
    private Date date;

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
                .appendSuper( getJournal().equals(other.getJournal() ) )
                .appendSuper( getSubscriber().equals(other.getSubscriber()) )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper (getJournal().hashCode())
                .appendSuper (getSubscriber().hashCode())
                .toHashCode();
    }

}
