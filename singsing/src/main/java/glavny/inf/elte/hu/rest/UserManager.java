package glavny.inf.elte.hu.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import glavny.inf.elte.hu.data.PasswordRepository;
import glavny.inf.elte.hu.data.User;
import glavny.inf.elte.hu.data.UserRepository;

@RestController
@RequestMapping("user")
@Transactional
public class UserManager {
	private static Logger log = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordRepository passDao;

	private String printUser(User u) {
		return "{ \"username\":\"" + u.getUsername() + "\"}";
	}

	@GetMapping("/self")
	public String selfUser(Authentication a) {
		User u = userDao.getOne(a.getName());
		return printUser(u);
	}

	@PostMapping("/change")
	public ResponseEntity<Void> changePass(@RequestParam("password") String password, Authentication a) {
		HttpHeaders headers = new HttpHeaders();
		ArrayList<String> passwords = passDao.findPasswordByUserName(a.getName());

		if (passwords != null) {
			if (!passwords.contains("{noop}" + password)) {

				passDao.addNewPassword(a.getName(), "{noop}" + password);
				userDao.changePassword(a.getName(), "{noop}" + password);
				try {
					headers.setLocation(new URI("/"));
				} catch (URISyntaxException e) {
					log.warn("Dispatcher cannot redirect");
				}
			} else {
				try {
					headers.setLocation(new URI("/password"));
				} catch (URISyntaxException e) {
					log.warn("Dispatcher cannot redirect");
				}
			}

			if (passwords.size() > 4) {
				passDao.removePassword(a.getName(), passwords.get(0));
			}
		}

		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
	}

	@PostMapping("/password")
	public ResponseEntity<Void> setSelfpassword(@RequestBody PassDTO pass, Authentication a) {
		User u = userDao.getOne(a.getName());
		u.setPassword("{noop}" + pass.getNew_pass());
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/userPrivileges")
	public ResponseEntity<String> userIsGuard() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		User findByName = userDao.findUserByName(name);

		String result = (findByName != null ? findByName.getUserGroup().toString() : "ERROR");

		log.info("User is guard: " + result);

		return new ResponseEntity<String>("{\"result\":\"" + result + "\"}", HttpStatus.OK);
	}

	@PostMapping("/dispatch")
	public ResponseEntity<Void> dispatchUser() {
		// log.debug("Into URI: " + rr.getURI().toString() );
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();

		User user = userDao.findUserByName(cc.getAuthentication().getName());
		Date date = new Date();
		long timeMilli = date.getTime();

		long difference = timeMilli - user.getRegistration().getTime();

		if (difference > (30 * 24 * 60 * 60)) {
			try {
				headers.setLocation(new URI("/password"));
			} catch (URISyntaxException e) {
				log.warn("Dispatcher cannot redirect");
			}
		} else {

			if (cc.getAuthentication() != null) {
				Authentication a = cc.getAuthentication();
				try {
					headers.setLocation(new URI("/"));
				} catch (URISyntaxException e) {
					log.warn("Dispatcher cannot redirect");
				}
			}
		}

		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
	}

}