package glavny.inf.elte.hu.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic
	@Column(name = "USERNAME")
	private String username;

	@Basic
	@Column(name = "PASSWORD")
	private String password;

	@Basic
	@Column(name = "REGISTRATION")
	private Timestamp registration;

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(username, user.username) && Objects.equals(password, user.password)
				&& Objects.equals(registration, user.registration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, registration);
	}
}
