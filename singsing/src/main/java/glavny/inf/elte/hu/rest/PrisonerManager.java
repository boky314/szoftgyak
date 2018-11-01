package glavny.inf.elte.hu.rest;


import glavny.inf.elte.hu.data.Area;
import glavny.inf.elte.hu.data.Prisoncell;
import glavny.inf.elte.hu.data.PrisoncellRepository;
import glavny.inf.elte.hu.data.Prisoner;
import glavny.inf.elte.hu.data.PrisonerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("prisoner")
@Transactional
public class PrisonerManager {
    private static Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private PrisonerRepository prisonerRepository;
    @Autowired
    private PrisoncellRepository prisoncellRepository;

    @GetMapping("/")
    public  ResponseEntity<List<Prisoner>> getPrisoners(Authentication auth)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Prisoner> result = prisonerRepository.findPrisonerByReleaseDateAfter(timestamp);
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }
    @GetMapping("/release_date/{time_stamp}")
    public ResponseEntity<List<Prisoner>> findPrisonerWithReleaseDate(@PathVariable("time_stamp") Long time, Authentication auth)
    {
        List<Prisoner> result = prisonerRepository.findPrisonerByReleaseDateBetween(new Timestamp(time),new Timestamp(Long.MAX_VALUE));
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Prisoner>> findPrisonerWithName(@PathVariable("name") java.lang.String name, Authentication auth)
    {
        List<Prisoner> result = prisonerRepository.findPrisonerByPrisonerName(name);
        return new ResponseEntity<List<Prisoner>>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prisoner> findPrisoner(@PathVariable("id") Integer id, Authentication auth) {

        Prisoner b = prisonerRepository.findById(id).get();
//		if(b == null) throw new ResourceNotFoundException( "Beteg "+taj+" not found"  );
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            throw new AccessDeniedException("No prisoner with ID: "+id);
        }
        b.getPrisonerName();
        return new ResponseEntity<Prisoner>(b, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Void> createPrisoner(@RequestBody Prisoner b, UriComponentsBuilder builder) {
        boolean flag = true;
        Prisoncell c = prisoncellRepository.getOne(b.getCellId());
        if(c.getPrisoners().size() >=  c.getSpace())
        {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        b.setCell(c);
        b.setPlaceDate(new Timestamp(System.currentTimeMillis()));

        prisonerRepository.save(b);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(builder.path("/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> savePrisoner(@RequestBody Prisoner r)
    {
        Prisoncell c = prisoncellRepository.getOne(r.getCellId());
        if(c.getPrisoners().size() >=  c.getSpace())
        {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        r.setCell(c);
        prisonerRepository.save(r);

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deletePrisoner(@RequestBody Prisoner p, UriComponentsBuilder builder) {
        prisonerRepository.delete(p);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
