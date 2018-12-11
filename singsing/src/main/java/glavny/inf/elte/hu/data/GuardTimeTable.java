package glavny.inf.elte.hu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GuardTimeTable implements Serializable {
    private static final long serialVersionUID = 1L;

    private PrisonGuard guard;
    private List<List<List<TimetableEntry>>> workTime;

    public int[] getExtraWorkPerWeek() {
        return extraWorkPerWeek;
    }

    private int[] extraWorkPerWeek;

    private int workPerDay;
    private int workPerWeek;

    public PrisonGuard getGuard() {
        return guard;
    }

    public List<List<List<TimetableEntry>>> getWorkTime() {
        return workTime;
    }

    public GuardTimeTable(PrisonGuard guard)
    {
        this.guard = guard;
        workTime =  new ArrayList<>();
        extraWorkPerWeek = new int[4];
        workPerDay = guard.getWorkPerDay();
        workPerWeek = guard.getWorkPerWeek();
        for(int week = 0; week < 4; ++week )
        {
            workTime.add(new ArrayList<>());
            for(int day = 0; day < 7; ++day )
            {
                workTime.get(week).add(new LinkedList<>());
            }
        }
    }

    public boolean addWorkEntry( TimetableEntry entry)
    {
        int week =  entry.getWeek();
        int day  =  entry.getDay();
        int time = entry.getEnd() - entry.getStart();
        if( getTotalWorkOnDayOfWeek(week, day) + time > workPerDay ) return false;
        if( getTotalWorkOnWeek(week) + time > workPerWeek ) return false;
        return addSafe(entry);
    }

    public boolean addExtraWorkSafe(TimetableEntry entry)
    {
        return addExtraWork(entry, true);
    }

    public boolean addExtraWorkHard(TimetableEntry entry)
    {
        return addExtraWork(entry, false);
    }

    private boolean addExtraWork(TimetableEntry entry, boolean safe)
    {
        boolean l = false;
        if(safe)
            l = addSafe(entry);
        else
            l = workTime.get(entry.getWeek()).get(entry.getDay()).add(entry);
        if(l)
        {
             extraWorkPerWeek[entry.getWeek()] += (entry.getEnd() - entry.getStart());
             entry.setExtraWork(true);
        }
        return l;
    }

    private int getTotalWorkOnDayOfWeek(int week, int day)
    {
        List<TimetableEntry> d = workTime.get(week).get(day);
        int result = 0;
        for(TimetableEntry e :  d)
            if(!e.isExtraWork())
                result += (e.getEnd() - e.getStart());
        return result;
    }

    private int getTotalWorkOnWeek(int week){
        List<List<TimetableEntry>> w = workTime.get(week);
        int result = 0;
        for(int day = 0; day < 7; ++day)
            result += getTotalWorkOnDayOfWeek(week,day);
        return result;
    }


    private boolean addSafe(TimetableEntry entry){
        List<TimetableEntry> shifts = workTime.get(entry.getWeek()).get(entry.getDay());
        for( TimetableEntry  e :  shifts) {
            if (e.getStart() == entry.getStart() || e.getEnd() == entry.getEnd()) return false;
        }
        return shifts.add(entry);
    }

}
