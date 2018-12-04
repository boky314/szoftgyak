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
@Table(name = "holiday")
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Basic
	@Column(name = "GUARD_NAME")
	private String guardName;

	@Basic
	@Column(name = "FROM_DATE")
	private Timestamp fromDate;

	@Basic
	@Column(name = "TO_DATE")
	private Timestamp toDate;

	@Basic
	@Column(name = "REGISTER_DATE")
	private Timestamp registerDate;

	@Column(name = "STATUS")
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuardName() {
		return guardName;
	}

	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((guardName == null) ? 0 : guardName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return (int) result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Holiday other = (Holiday) obj;
		if (id != other.id)
			return false;

		if (guardName == null) {
			if (other.guardName != null)
				return false;
		} else if (!guardName.equals(other.guardName))
			return false;

		if (fromDate != other.fromDate)
			return false;
		if (toDate != other.toDate)
			return false;
		if (registerDate != other.registerDate)
			return false;
		if (status != other.status)
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Holiday [id=" + id + ", guard=" + guardName + ", registerDate=" + registerDate + ", fromTime="
				+ fromDate + ", toTime=" + toDate + ", registerDate=" + registerDate + ", status=" + status + "]";
	}

}
