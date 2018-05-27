package glavny.inf.elte.hu.rest;

import java.net.URI;
import java.net.URISyntaxException;

import glavny.inf.elte.hu.data.User;
import glavny.inf.elte.hu.data.UserRepository;



import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Transactional
public class UserManager {
    private static Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserRepository userDao;

    private String printUser(User u) {
        return "{ \"username\":\"" +u.getUsername() + "\"}";
    }

    @GetMapping("/self")
    public String selfUser(Authentication a) {
        User u = userDao.getOne(a.getName());
        return printUser(u);
    }

    @PostMapping("/password")
    public ResponseEntity<Void> setSelfpassword(@RequestBody PassDTO pass, Authentication a) {
        User u = userDao.getOne(a.getName());
        u.setPassword("{noop}"+pass.getNew_pass());
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Void> dispatchUser() {
        //log.debug("Into URI: " + rr.getURI().toString() );
        SecurityContext cc = SecurityContextHolder.getContext();
        HttpHeaders headers = new HttpHeaders();
        if(cc.getAuthentication() != null) {
            Authentication a=cc.getAuthentication();
            try
            {
                headers.setLocation(new URI("/"));
            }
            catch ( URISyntaxException e )	{ log.warn( "Dispatcher cannot redirect" ); }
        }

        return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
    }

}