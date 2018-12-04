package glavny.inf.elte.hu.rest;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import glavny.inf.elte.hu.data.AuditLog;
import glavny.inf.elte.hu.data.AuditLogRepository;
import glavny.inf.elte.hu.data.Holiday;
import glavny.inf.elte.hu.data.HolidayRepository;
import glavny.inf.elte.hu.data.PrisonGuard;
import glavny.inf.elte.hu.data.PrisonGuardRepository;

@RestController
@RequestMapping("holiday")
@Transactional
public class HolidayManager {

	private static Logger log = LoggerFactory.getLogger(HolidayManager.class);

	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired
	private AuditLogRepository auditLogRepository;
	@Autowired
	private PrisonGuardRepository guardRepository;

	@GetMapping("/")
	public ResponseEntity<List<Holiday>> getHolidays(Authentication auth) {
		String guardName = SecurityContextHolder.getContext().getAuthentication().getName();

		List<PrisonGuard> findByName = guardRepository.findByName(guardName);

		if (findByName != null && findByName.size() == 1)
			return new ResponseEntity<List<Holiday>>(holidayRepository.findByGuardName(guardName), HttpStatus.OK);
		else
			return new ResponseEntity<List<Holiday>>(holidayRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/new")
	public ResponseEntity<Void> createHoliday(@RequestBody Holiday h, UriComponentsBuilder builder) {
		String guardName = SecurityContextHolder.getContext().getAuthentication().getName();

		h.setGuardName(guardName);
		holidayRepository.save(h);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<Void> updateHoliday(@RequestBody Holiday h, UriComponentsBuilder builder) {
//		String name = SecurityContextHolder.getContext().getAuthentication().getName();

//		auditLogRepository
//				.save(new AuditLog(name, new Timestamp(System.currentTimeMillis()), "APPROVE", h.toString()));

		holidayRepository.save(h);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{guardName}")
	public ResponseEntity<List<Holiday>> getHolidaysForGuard(@PathVariable("guardName") String guardName,
			Authentication auth) {
		List<Holiday> result = holidayRepository.findByGuardName(guardName);
		return new ResponseEntity<List<Holiday>>(result, result != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{fromDate}/{toDate}")
	public ResponseEntity<List<Holiday>> findHolidayBetweenDate(@PathVariable("fromDate") Long fromDate,
			@PathVariable("toDate") Long toDate, Authentication auth) {
		List<Holiday> result = holidayRepository.findHolidayDateBetween(new Timestamp(fromDate), new Timestamp(toDate));
		return new ResponseEntity<List<Holiday>>(result, HttpStatus.OK);
	}

	@GetMapping("/{fromDate}")
	public ResponseEntity<List<Holiday>> findHolidayFrom(@PathVariable("fromDate") Long fromDate, Authentication auth) {
		List<Holiday> result = holidayRepository.findHolidayFrom(new Timestamp(fromDate));
		return new ResponseEntity<List<Holiday>>(result, HttpStatus.OK);
	}

}
