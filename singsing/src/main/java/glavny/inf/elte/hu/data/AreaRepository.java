package glavny.inf.elte.hu.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    public List<Area> findAll();

    public Optional<Area> findById(Integer id);
}
