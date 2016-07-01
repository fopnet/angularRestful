package ngdemo.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Felipe on 01/07/2016.
 */
@Embeddable
public class SubscriptionID implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userID;
    private Long journalID;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getJournalID() {
        return journalID;
    }

    public void setJournalID(Long journalID) {
        this.journalID = journalID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubscriptionID)) {
            return false;
        }
        SubscriptionID other  = (SubscriptionID) o;
        return new EqualsBuilder()
                .append ( getJournalID() , other.getJournalID() )
                .append(getUserID(), other.getUserID())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper (getJournalID().hashCode())
                .appendSuper (getUserID().hashCode())
                .toHashCode();
    }
}
