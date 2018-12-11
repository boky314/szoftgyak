package glavny.inf.elte.hu.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimetableEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private int week;
    private int day;
    private int start;
    private int end;
    private String areaName;
    private int level;
    private boolean extraWork;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    private TimetableEntry cloneData ()
    {
        TimetableEntry timetableEntry =  new TimetableEntry();
        timetableEntry.areaName =  this.areaName;
        timetableEntry.day = this.day;
        timetableEntry.week = this.week;
        timetableEntry.level = this.level;
        timetableEntry.extraWork = this.extraWork;
        return timetableEntry;
    }
    @JsonIgnore
    public TimetableEntry getFirstHalf()
    {
        TimetableEntry e =  cloneData();
        e.start = this.start;
        e.end = e.start + (this.end - this.start) / 2;
        return e;
    }
    @JsonIgnore
    public TimetableEntry getSecondHalf()
    {
        TimetableEntry e =  cloneData();
        e.end = this.end;
        e.start = e.end - (this.end - this.start) / 2;
        return e;
    }

    public boolean isExtraWork() {
        return extraWork;
    }

    public void setExtraWork(boolean extraWork) {
        this.extraWork = extraWork;
    }

    public Timestamp getStartTimestamp() {
        LocalDate ld = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
        LocalDate currentDate = ld.plusWeeks(week).plusDays(day);
        return Timestamp.valueOf(currentDate.atStartOfDay());
    }

}
