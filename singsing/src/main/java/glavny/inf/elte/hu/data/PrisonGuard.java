package glavny.inf.elte.hu.data;

import javax.persistence.*;

@Entity
@Table(name = "prisonguard")
public class PrisonGuard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "PRISONGUARD_NAME")
    private String name;

    @Basic
    @Column(name =  "WORK_PER_DAY")
    private int workPerDay;

    @Basic
    @Column(name =  "WORK_PER_WEEK")
    private int workPerWeek;


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

    @Override
    public String toString() {
        return "PrisonGuard [name=" + name + "]";
    }

    public int getWorkPerDay() {
        return workPerDay;
    }

    public void setWorkPerDay(int workPerDay) {
        this.workPerDay = workPerDay;
    }

    public int getWorkPerWeek() {
        return workPerWeek;
    }

    public void setWorkPerWeek(int workPerWeek) {
        this.workPerWeek = workPerWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
