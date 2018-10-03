package glavny.inf.elte.hu.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PrisonGuardRepository extends JpaRepository<PrisonGuard, String> {
	public List<PrisonGuard> findAll();
    public List<PrisonGuard> findByName(String name);
}
