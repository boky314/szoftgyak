package glavny.inf.elte.hu.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import glavny.inf.elte.hu.data.AuditLog;
import glavny.inf.elte.hu.data.AuditLogRepository;

@RestController
@RequestMapping("auditlog")
@Transactional
public class AuditLogManager {
	
	@Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/")
    public  ResponseEntity<List<AuditLog>> getLogs(Authentication auth)
    {
    	//System.out.println("MIVAN------");
        List<AuditLog> result = auditLogRepository.findAll();
        //System.out.println("------------->"+result.toString());
        return new ResponseEntity<List<AuditLog>>(result, HttpStatus.OK);
    }
}
