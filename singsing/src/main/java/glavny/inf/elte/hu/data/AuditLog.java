package glavny.inf.elte.hu.data;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auditlog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    
    @Basic
    @Column(name = "USER")
    private String user;

    @Basic
    @Column(name = "DATETIME")
    private Timestamp dateTime;
    
    @Basic
    @Column(name = "CHANGETYPE")
    private String changeType;
    
    @Basic
    @Column(name = "CHANGE")
    private String change;
    
    public AuditLog()
    {
    	
    }
  
    public AuditLog(String user, Timestamp dateTime, String changeType, String change) {
        setUser(user);
        setDateTime(dateTime);
        setChangeType(changeType);
        setChange(change);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public Timestamp getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getChangeType() {
        return changeType;
    }
    
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
    
    public String getChange() {
        return change;
    }
    
    public void setChange(String change) {
        this.change = change;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + ((change == null) ? 0 : change.hashCode());
        result = prime * result + dateTime.hashCode();
        result = prime * result + id;
        result = prime * result + ((changeType == null) ? 0 : changeType.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return (int) result;
    }

  @Override
	public String toString() {
		return "AuditLog [id=" + id + ", user=" + user + ", dateTime=" + dateTime + ", changeType=" + changeType
				+ ", change=" + change + "]";
	}
	@Override
  public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuditLog other = (AuditLog) obj;
        if (change == null) {
            if (other.change != null)
                return false;
        } else if (!change.equals(other.change))
            return false;
    
        if (dateTime != other.dateTime)
            return false;
        if (id != other.id)
            return false;
        if (changeType != other.changeType)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
