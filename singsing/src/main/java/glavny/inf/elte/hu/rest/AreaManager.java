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

import glavny.inf.elte.hu.data.Area;
import glavny.inf.elte.hu.data.AreaRepository;
import glavny.inf.elte.hu.data.AuditLog;
import glavny.inf.elte.hu.data.AuditLogRepository;
import glavny.inf.elte.hu.data.PrisoncellRepository;

@RestController
@RequestMapping("area")
@Transactional
public class AreaManager {
    private static Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private PrisoncellRepository prisoncellRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;


    @GetMapping("/")
    public ResponseEntity<List<Area>> getPrisonCells(Authentication auth) {
        return new ResponseEntity<List<Area>>(areaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> getPrisonCell(@PathVariable("id") Integer id, Authentication auth) {
        Area result = areaRepository.findById(id).get();
        return new ResponseEntity<Area>(result, result != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createPrisonCell(@RequestBody Area c, UriComponentsBuilder builder) {
    	String user  = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog(user,new Timestamp(System.currentTimeMillis()), "CREATE", c.toStringForLog()));
        
        areaRepository.save(c);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updatePrisonCell(@RequestBody Area c, UriComponentsBuilder builder) {
    	String user  = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog(user,new Timestamp(System.currentTimeMillis()), "MODIFY", c.toStringForLog()));
        
        areaRepository.save(c);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deletePrisonCell(@RequestBody Area c, UriComponentsBuilder builder) {
    	String user  = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog(user,new Timestamp(System.currentTimeMillis()), "DELETE", c.toStringForLog()));
    	
        HttpHeaders headers = new HttpHeaders();
        int cells = prisoncellRepository.countCellByAreaId(c.getId());
        if (cells > 0) {
            return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
        }
        else {
            areaRepository.delete(c);
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
    }
}
