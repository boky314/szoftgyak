package glavny.inf.elte.hu.rest;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import glavny.inf.elte.hu.data.Area;
import glavny.inf.elte.hu.data.AreaRepository;
import glavny.inf.elte.hu.data.GuardTimeTable;
import glavny.inf.elte.hu.data.Holiday;
import glavny.inf.elte.hu.data.HolidayRepository;
import glavny.inf.elte.hu.data.PrisonGuard;
import glavny.inf.elte.hu.data.PrisonGuardRepository;
import glavny.inf.elte.hu.data.Prisoncell;
import glavny.inf.elte.hu.data.PrisoncellRepository;
import glavny.inf.elte.hu.data.Prisoner;
import glavny.inf.elte.hu.data.TimetableEntry;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("schedule")
@Transactional
public class ScheduleManager {
    private static Logger log = LoggerFactory.getLogger(ScheduleManager.class);

    @Autowired
    private PrisoncellRepository prisoncellRepository; // TODO get all space where we need guard method
    @Autowired
    private PrisonGuardRepository prisonGuardRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private HolidayRepository holidayRepository;

    @GetMapping("/")
    ResponseEntity<List<GuardTimeTable>> getSchedule(Authentication auth) {
        List<GuardTimeTable> result = generateSchedule(null);

        if(result != null) {
            return new ResponseEntity<List<GuardTimeTable>>(result, HttpStatus.OK);
        }

        return new ResponseEntity<List<GuardTimeTable>>(result, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkholiday")
    ResponseEntity<Void> checkHoliday(@RequestBody Holiday h, UriComponentsBuilder builder) {

        List<GuardTimeTable> result = generateSchedule(h);
        HttpHeaders headers = new HttpHeaders();

        if(result != null) {
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        }

        return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
    }

    List<GuardTimeTable> generateSchedule(Holiday holiday) {
        List<GuardedAreaDTO> guardedAreas = findGuardedAreas();
        List<PrisonGuard> guards = prisonGuardRepository.findAll();
        List<TimetableEntry> allShift = new LinkedList<>();

        Queue<GuardTimeTable> rotatingTimeTables = new LinkedList<>();
        List<GuardTimeTable> result = new ArrayList<>();
        for (PrisonGuard g : guards) {
            GuardTimeTable t = new GuardTimeTable(g);
            result.add(t);
            rotatingTimeTables.add(t);
        }

        // calculate all shift
        LocalDate ld = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 7; ++j) {
                LocalDate currentDate = ld.plusWeeks(i).plusDays(j);
                Timestamp ts = Timestamp.valueOf(currentDate.atStartOfDay());
                for (GuardedAreaDTO ga : guardedAreas) {
                    int guardsNumber = guardsNeededByArea(ga, ts);
                    for (int k = 0; k < 3; ++k)
                        for(int g = 0; g < guardsNumber; ++g ){
                            TimetableEntry shift = new TimetableEntry();
                            shift.setWeek(i);
                            shift.setDay(j);
                            shift.setStart(k * 8);
                            shift.setEnd(k * 8 + 8);
                            shift.setLevel(ga.getLevel());
                            shift.setAreaName(ga.getAreaName());
                            allShift.add(shift);
                        }
                }
            }
        }

        BiFunction<Queue<GuardTimeTable>, TimetableEntry, GuardTimeTable> getNextTimetable = (timetable, shift) -> {
            for (int i = 0; i < timetable.size(); ++i) {
                GuardTimeTable t = timetable.remove();
                timetable.add(t);

                // Skip when the guard is on holiday
                Timestamp date = shift.getStartTimestamp();
                final long offset = 24*3600*1000; // One day in milliseconds
                // Need a one day width argument
                Timestamp date2 = new Timestamp(date.getTime() - offset);
                String name = t.getGuard().getPrisonGuardName();
                List<Holiday> h = holidayRepository.findByGuardByDate(name, date, date2);

                if (h.size() == 0 && holiday == null) {
                    return t;
                }

                if(holiday != null) {
                    if(!(holiday.getGuardName().equals(name) && holiday.getStatus().equals("APPROVED") &&
                            holiday.getFromDate().compareTo(date) <= 0 &&  holiday.getToDate().compareTo(date2) > 0)) {
                        return t;
                    }
                }
            }
            return null;
        };

        Function<TimetableEntry, TimetableEntry> findNext = elem -> {
            for (int i = 0; i < rotatingTimeTables.size(); ++i) {
                GuardTimeTable t = getNextTimetable.apply(rotatingTimeTables, elem);
                if (t != null && t.addWorkEntry(elem))
                    return null;
            }
            return elem;
        };

        // try to place all 8 hour long shits
        allShift = allShift.stream().filter(e -> {
            return findNext.apply(e) != null;
        }).collect(Collectors.toList());

        // chop into two 4 hour long shift
        allShift = allShift.stream().filter(e -> {
            // split
            TimetableEntry first = findNext.apply(e.getFirstHalf());
            if (first == null) {
                TimetableEntry second = findNext.apply(e.getSecondHalf());
                if (second == null)
                    return false;
                else {
                    e.setStart(second.getStart());
                    e.setEnd(second.getEnd());
                }
            }
            return true;
        }).collect(Collectors.toList());

        // Add the extra work with zero overlaps
        allShift = allShift.stream().filter(e -> {
            for (int i = 0; i < rotatingTimeTables.size(); ++i) {
                GuardTimeTable t = getNextTimetable.apply(rotatingTimeTables, e);
                if (t != null && t.addExtraWorkSafe(e))
                    return false;
            }
            return true;
        }).collect(Collectors.toList());

//        // Add the extra work with force
//        allShift = allShift.stream().filter(e -> {
//            GuardTimeTable t = getNextTimetable.apply(rotatingTimeTables, e);
//            if (t != null) {
//                return !t.addExtraWorkHard(e);
//            }
//            return true;
//        }).collect(Collectors.toList());
        if(!allShift.isEmpty()) {
            return null;
        }

        return result;
    }

    // Determinate all possible guarded area
    private List<GuardedAreaDTO> findGuardedAreas() {
        List<GuardedAreaDTO> result = new ArrayList<>();
        List<Area> areas = areaRepository.findAll();
        for (Area a : areas) {
            Set<Integer> levels = new HashSet<>();
            Set<Prisoncell> prisonCells = a.getPrisonCells();
            for (Prisoncell cell : prisonCells)
                levels.add(cell.getFloor());
            for (Integer l : levels)
                result.add(new GuardedAreaDTO(a.getId(), a.getName(), l, a.getGuardNumber()));
        }
        return result;
    }

    private int guardsNeededByArea(GuardedAreaDTO ga, Timestamp ts) {
        Optional<Area> byId = areaRepository.findById(ga.getAreaID());
        if (!byId.isPresent())
            return 0;
        Set<Prisoncell> prisonCells = byId.get().getPrisonCells().stream().filter(e -> ga.getLevel() == e.getFloor())
                .collect(Collectors.toSet());
        int prisonersTotal = 0;
        int extraGuardNumber = 0;
        for (Prisoncell cell : prisonCells) {
            Set<Prisoner> prisoners = cell.getPrisoners();
            Timestamp tsEnd = Timestamp.valueOf(ts.toLocalDateTime().plusDays(1));
            for (Prisoner p : prisoners)
                if (!(p.getPlaceDate().after(tsEnd) || ts.after(p.getReleaseDate()))) {
                    ++prisonersTotal;
                    extraGuardNumber += p.getGuardNumber();
                }
        }
        if (prisonersTotal > 0)
            return byId.get().getGuardNumber() + extraGuardNumber;
        return 0;
        // return false;
    }
}
