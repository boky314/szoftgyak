package glavny.inf.elte.hu.rest;


import glavny.inf.elte.hu.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Guard;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("schedule")
@Transactional
public class ScheduleManager {
    private static Logger log = LoggerFactory.getLogger(ScheduleManager.class);

    @Autowired
    private PrisoncellRepository prisoncellRepository; //TODO get all space where we need guard method
    @Autowired
    private PrisonGuardRepository prisonGuardRepository;
    @Autowired
    private AreaRepository areaRepository;


    @GetMapping("/")
    ResponseEntity<List<GuardTimeTable>> getSchedule(Authentication auth)
    {
        List<GuardedAreaDTO> guardedAreas = findGuardedAreas();
        List<PrisonGuard> gourds =  prisonGuardRepository.findAll();
        List<TimetableEntry> allShift = new LinkedList<>();


        Queue<GuardTimeTable> rotatingTimeTables = new LinkedList<>();
        List<GuardTimeTable>  result =  new ArrayList<>();
        for(PrisonGuard g : gourds)
        {
            GuardTimeTable t  = new GuardTimeTable(g);
            result.add( t );
            rotatingTimeTables.add(t);
        }

        //calculate all shift
        LocalDate ld =  LocalDate.now().with(DayOfWeek.MONDAY);
        for(int i = 0; i < 4; ++i)
        {
            for(int j = 0; j < 7; ++j)
            {
                LocalDate currentDate = ld.plusWeeks(i).plusDays(j);
                Timestamp ts = Timestamp.valueOf(currentDate.atStartOfDay());
                for(GuardedAreaDTO ga : guardedAreas)
                {
                    if(!guardedAreaHasPrisoner(ga, ts)) continue;
                    for(int k = 0; k < 3; ++k)
                    {
                        TimetableEntry shift =  new TimetableEntry();
                        shift.setWeek(i);
                        shift.setDay(j);
                        shift.setStart(k*8);
                        shift.setEnd(k*8+8);
                        shift.setLevel(ga.getLevel());
                        shift.setAreaName(ga.getAreaName());
                        allShift.add(shift);
                    }
                }
            }
        }

        Function<TimetableEntry,TimetableEntry> findNext = elem ->{
            for(int i = 0;  i < rotatingTimeTables.size(); ++i)
            {
                GuardTimeTable t = rotatingTimeTables.remove();
                rotatingTimeTables.add(t);
                if(t.addWorkEntry(elem)) return null;
            }
            return elem;
        };


        //try to place all 8 hour long shits
        allShift = allShift.stream().filter(e->{
            return findNext.apply(e) != null;
        }).collect(Collectors.toList());


        //chop into two 4 hour long shift
        allShift =  allShift.stream().filter(e->{
            //split
            TimetableEntry first = findNext.apply(e.getFirstHalf());
            if(first == null)
            {
                TimetableEntry second = findNext.apply(e.getSecondHalf());
                if(second == null) return false;
                else {
                    e.setStart(second.getStart());
                    e.setEnd(second.getEnd());
                }
            }
            return true;
        }).collect(Collectors.toList());

        //Add the extra work to gourds
        allShift =  allShift.stream().filter(e->{
            GuardTimeTable t = rotatingTimeTables.remove();
            rotatingTimeTables.add(t);
            return !t.addExtraWork(e);
        }).collect(Collectors.toList());

        return new ResponseEntity<List<GuardTimeTable>>(result,HttpStatus.OK);
    }

    //Determinate all possible guarded area
    private List<GuardedAreaDTO> findGuardedAreas()
    {
        List<GuardedAreaDTO> result =  new ArrayList<>();
        List<Area> areas = areaRepository.findAll();
        for(Area a : areas)
        {
            Set<Integer> levels  = new HashSet<>();
            Set<Prisoncell> prisonCells = a.getPrisonCells();
            for( Prisoncell cell : prisonCells)
                levels.add(cell.getFloor());
            for(Integer l :  levels)
                result.add(new GuardedAreaDTO(a.getId(), a.getName(),l, 1));
        }
        return result;
    }

    private boolean guardedAreaHasPrisoner(GuardedAreaDTO ga, Timestamp ts)
    {
        Optional<Area> byId = areaRepository.findById(ga.getAreaID());
        if(!byId.isPresent())
            return false;
        Set<Prisoncell> prisonCells =
                byId.get().getPrisonCells().stream().filter(e-> ga.getLevel() == e.getFloor()).collect(Collectors.toSet());
        for(Prisoncell cell : prisonCells)
        {
            Set<Prisoner> prisoners = cell.getPrisoners();
            Timestamp tsEnd = Timestamp.valueOf(ts.toLocalDateTime().plusDays(1));
            for(Prisoner p : prisoners)
                if(!( p.getPlaceDate().after(tsEnd) || ts.after(p.getReleaseDate())))
                    return true;
        }
        return false;

        //return false;
    }
}
