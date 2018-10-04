package glavny.inf.elte.hu.data;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import java.util.stream.Collectors;

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
}
