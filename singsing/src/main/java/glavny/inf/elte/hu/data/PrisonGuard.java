package glavny.inf.elte.hu.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prisonguard")
public class PrisonGuard {
    @Id
    @Column(name = "PRISONGUARD_NAME")
    private String name;

    public String getPrisonGuardName() {
        return name;
    }
    public void setPrisonGuardName(String prisonGuardName) {
        this.name = prisonGuardName;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PrisonGuard other = (PrisonGuard) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
