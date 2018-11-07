package glavny.inf.elte.hu.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area")
public class Area {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Basic
	@Column(name = "NAME")
	private String name;

	@Basic
	@Column(name = "SECURITY_LEVEL")
	private String areaSecurity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaSecurity() {
		return areaSecurity;
	}

	public void setAreaSecurity(String areaSecurity) {
		this.areaSecurity = areaSecurity;
	}

	

}
