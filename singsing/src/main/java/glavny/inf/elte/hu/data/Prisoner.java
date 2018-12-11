package glavny.inf.elte.hu.data;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "prisoner")
public class Prisoner {	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "PRISONER_NAME")
    private String prisonerName;
    @Basic
    @Column(name = "INCIDENT")
    private String incident;

    @Basic
    @Column(name = "RELEASE_DATE")
    private Timestamp releaseDate;

    @Basic
    @Column(name = "PLACE_DATE")
    private Timestamp placeDate;
    
	  @Basic
	  @Column(name = "SECURITY_LEVEL")
    private String prisonerSecurity;

    @Column(name = "CELL_ID", insertable = false, updatable = false)
    private int cellId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CELL_ID",nullable = false)
    @JsonIgnore
    private Prisoncell cell;

    @Basic
    @Column(name = "GUARD_NUMBER")
    private int guardNumber;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPrisonerName() {
        return prisonerName;
    }
    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }


    public String getIncident() {
        return incident;
    }
    public void setIncident(String incident) {
        this.incident = incident;
    }


    public Timestamp getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Timestamp getPlaceDate() {
        return placeDate;
    }
    public void setPlaceDate(Timestamp placeDate) {
        this.placeDate = placeDate;
    }

    public Prisoncell getCell() {
        return cell;
    }

    public void setCell(Prisoncell cell) {
        this.cell = cell;
    }

    public String getPrisonerSecurity() {
        return prisonerSecurity;
    }

    public void setPrisonerSecurity(String prisonerSecurity) {
        this.prisonerSecurity = prisonerSecurity;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prisoner prisoner = (Prisoner) o;
        return id == prisoner.id &&
                Objects.equals(prisonerName, prisoner.prisonerName) &&
                Objects.equals(incident, prisoner.incident) &&
                Objects.equals(releaseDate, prisoner.releaseDate) &&
                Objects.equals(placeDate, prisoner.placeDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, prisonerName, incident, releaseDate, placeDate);
    }
    
    @Override
    public String toString() {
        return "Prisoner [id=" + id + ", prisonerName=" + prisonerName + ", incident=" + incident + ", releaseDate="
                + releaseDate + ", prisonerSecurity=" + prisonerSecurity + ", placeDate=" + placeDate + ", cellId=" + cellId + "]";
    }

    public int getGuardNumber() {
        return guardNumber;
    }

    public void setGuardNumber(int guardNumber) {
        this.guardNumber = guardNumber;
    }
}
