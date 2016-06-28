package ngdemo.domain;

import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
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
    private Journal journal;

    @ManyToOne
    @JoinColumn(name="USERID")
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
        return getJournal().equals(other.getJournal())
                && getSubscriber().equals(other.getSubscriber());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getJournal().hashCode())
               .append(getSubscriber().hashCode());
        return builder.toHashCode();
    }

}
