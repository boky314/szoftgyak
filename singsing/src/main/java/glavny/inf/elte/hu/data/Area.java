package glavny.inf.elte.hu.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    private Set<Prisoncell> prisonCells = new HashSet<>(0);

    public Set<Prisoncell> getPrisonCells() {
        return prisonCells;
    }

	  @Override
    public String toString() {
        return "Area [id=" + id + ", name=" + name + "]";
    }
}
