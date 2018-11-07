package glavny.inf.elte.hu.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Integer> {
    public List<AuditLog> findAll();

    public List<AuditLog> searchByString(String str);
}
