package glavny.inf.elte.hu.rest;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import glavny.inf.elte.hu.data.AuditLog;
import glavny.inf.elte.hu.data.AuditLogRepository;
import glavny.inf.elte.hu.data.PrisonGuard;
import glavny.inf.elte.hu.data.PrisonGuardRepository;
import glavny.inf.elte.hu.data.User;
import glavny.inf.elte.hu.data.UserGroup;
import glavny.inf.elte.hu.data.UserRepository;

@RestController
@RequestMapping("prisonguard")
@Transactional
public class PrisonGuardManager {
	private static Logger log = LoggerFactory.getLogger(PrisonGuardManager.class);

	@Autowired
	private PrisonGuardRepository prisonGuardRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuditLogRepository auditLogRepository;

	@GetMapping("/")
	public ResponseEntity<List<PrisonGuard>> getPrisoners(Authentication auth) {
		List<PrisonGuard> result = prisonGuardRepository.findAll();
		return new ResponseEntity<List<PrisonGuard>>(result, HttpStatus.OK);
	}

	@PostMapping("/new")
	public ResponseEntity<Void> createPrisonGuard(@RequestBody PrisonGuard guard, Principal principal) {
		auditLogRepository.save(new AuditLog(principal.getName(), new Timestamp(System.currentTimeMillis()), "CREATE",
				guard.toString()));

		prisonGuardRepository.save(guard);
		User user = new User();
		user.setPassword("{noop}admin");
		user.setRegistration(new Timestamp(System.currentTimeMillis()));
		user.setUsername(guard.getPrisonGuardName());
		user.setUserGroup(UserGroup.GUARD);
		userRepository.save(user);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PostMapping("/delete")
	public ResponseEntity<Void> deletePrisonGuard(@RequestBody PrisonGuard guard, Principal principal) {
		auditLogRepository.save(new AuditLog(principal.getName(), new Timestamp(System.currentTimeMillis()), "DELETE",
				guard.toString()));

		prisonGuardRepository.delete(guard);
		User user = userRepository.findUserByName(guard.getPrisonGuardName());
		userRepository.delete(user);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Void> savePrisoner(@RequestBody PrisonGuard g, Principal principal) {
		auditLogRepository.save(
				new AuditLog(principal.getName(), new Timestamp(System.currentTimeMillis()), "MODIFY", g.toString()));

		prisonGuardRepository.save(g);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}



}
