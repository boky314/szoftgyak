package glavny.inf.elte.hu.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "prisoncell")
public class Prisoncell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "SPACE")
    private int space;

    @Basic
    @Column(name = "CELL_DESC")
    private String cellDesc;

    @Basic
    @Column(name = "FLOOR")
    private int floor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cell")
    private Set<Prisoner> prisoners = new HashSet<>(0);

    @Column(name = "AREA_ID", insertable = false, updatable = false)
    private int areaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID", nullable = false)
    @JsonIgnore
    private Area area;

    public Set<Prisoner> getPrisoners() {
        return prisoners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getCellDesc() {
        return cellDesc;
    }

    public void setCellDesc(String cellDesc) {
        this.cellDesc = cellDesc;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Prisoncell that = (Prisoncell) o;
        return id == that.id && space == that.space && Objects.equals(cellDesc, that.cellDesc) && floor == that.floor;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, space, cellDesc, floor);
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    @Override
    public String toString() {
        return "Prisoncell [id=" + id + ", space=" + space + ", cellDesc=" + cellDesc + ", prisoners=" + prisoners
                + "]";
    }
}
