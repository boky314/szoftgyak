package glavny.inf.elte.hu.data;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PrisonerRepository extends JpaRepository<Prisoner,Integer> {
    public List<Prisoner> findAll();
    public List<Prisoner> findPrisonerByPrisonerName(String name);
    @Query("select p from Prisoner p where p.releaseDate > :releaseDate and p.releaseDate < :releaseDate2")
    public List<Prisoner> findPrisonerByReleaseDateBetween(@Param("releaseDate") Timestamp releaseDate, @Param("releaseDate2")Timestamp releaseDate2);
    @Query("select p from Prisoner p where p.releaseDate > :releaseDate")
    public List<Prisoner> findPrisonerByReleaseDateAfter(@Param("releaseDate") Timestamp releaseDate);
    public Optional<Prisoner> findById(Integer id);
    @Query(value = "select count(*) from prisoner where release_date > current_timestamp()", nativeQuery = true)
    public int countPrisoners();
    @Query(value = "select count(id) from prisoner where CELL_ID = :id group by CELL_ID;", nativeQuery = true)
    Integer dispersion(@Param("id") int id);
}