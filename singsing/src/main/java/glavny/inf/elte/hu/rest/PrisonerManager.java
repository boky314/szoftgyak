package glavny.inf.elte.hu.rest;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

import glavny.inf.elte.hu.data.Area;
import glavny.inf.elte.hu.data.Prisoncell;
import glavny.inf.elte.hu.data.PrisoncellRepository;
import glavny.inf.elte.hu.data.Prisoner;
import glavny.inf.elte.hu.data.PrisonerRepository;
import glavny.inf.elte.hu.data.User;

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
import org.springframework.web.util.UriComponentsBuilder;

import glavny.inf.elte.hu.data.AuditLog;
import glavny.inf.elte.hu.data.AuditLogRepository;
import glavny.inf.elte.hu.data.ChangeType;
import glavny.inf.elte.hu.data.Prisoncell;
import glavny.inf.elte.hu.data.PrisoncellRepository;
import glavny.inf.elte.hu.data.Prisoner;
import glavny.inf.elte.hu.data.PrisonerRepository;

@RestController
@RequestMapping("prisoner")
@Transactional
public class PrisonerManager {
    private static Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private PrisonerRepository prisonerRepository;
    @Autowired
    private PrisoncellRepository prisoncellRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/")
    public ResponseEntity<List<Prisoner>> getPrisoners(Authentication auth) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Prisoner> result = prisonerRepository.findPrisonerByReleaseDateAfter(timestamp);
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }

    @GetMapping("/release_date/{time_stamp}")
    public ResponseEntity<List<Prisoner>> findPrisonerWithReleaseDate(@PathVariable("time_stamp") Long time,
            Authentication auth) {
        List<Prisoner> result = prisonerRepository.findPrisonerByReleaseDateBetween(new Timestamp(time),
                new Timestamp(Long.MAX_VALUE));
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Prisoner>> findPrisonerWithName(@PathVariable("name") java.lang.String name,
            Authentication auth) {
        List<Prisoner> result = prisonerRepository.findPrisonerByPrisonerName(name);
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prisoner> findPrisoner(@PathVariable("id") Integer id, Authentication auth) {

        Prisoner b = prisonerRepository.findById(id).get();
        // if(b == null) throw new ResourceNotFoundException( "Beteg "+taj+" not found"
        // );
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            throw new AccessDeniedException("No prisoner with ID: " + id);
        }
        b.getPrisonerName();
        return new ResponseEntity<Prisoner>(b, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createPrisoner(@RequestBody Prisoner p, UriComponentsBuilder builder,
            Principal principal) {
        boolean flag = true;
        Prisoncell c = prisoncellRepository.getOne(p.getCellId());
        if (c.getPrisoners().size() >= c.getSpace()) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        p.setCell(c);
        p.setPlaceDate(new Timestamp(System.currentTimeMillis()));

        prisonerRepository.save(p);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        auditLogRepository.save(
                new AuditLog(principal.getName(), new Timestamp(System.currentTimeMillis()), "CREATE", p.toString()));
        HttpHeaders headers = new HttpHeaders();
        // headers.setLocation(builder.path("/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> savePrisoner(@RequestBody Prisoner p, Principal principal) {
        auditLogRepository.save(
                new AuditLog(principal.getName(), new Timestamp(System.currentTimeMillis()), "MODIFY", p.toString()));

        Prisoncell c = prisoncellRepository.getOne(p.getCellId());

        if (c.getPrisoners().size() >= c.getSpace()) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        p.setCell(c);
        prisonerRepository.save(p);

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deletePrisoner(@RequestBody Prisoner p, UriComponentsBuilder builder) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog(user, new Timestamp(System.currentTimeMillis()), "DELETE", p.toString()));
        prisonerRepository.delete(p);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
