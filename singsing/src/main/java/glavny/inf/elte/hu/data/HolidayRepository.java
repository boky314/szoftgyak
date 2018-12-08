package glavny.inf.elte.hu.data;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

	public List<Holiday> findAll();

	public List<Holiday> findByGuardName(String guardName);

	@Query("select h from Holiday h where h.fromDate >= :fromDate and h.toDate <= :toDate")
	public List<Holiday> findHolidayDateBetween(@Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);

	@Query("select h from Holiday h where h.fromDate > :fromDate")
	public List<Holiday> findHolidayFrom(@Param("fromDate") Timestamp fromDate);

	@Query(value="select * from Holiday where from_date <= :date and to_date > :date and guard_name = :name and status = 'APPROVED';", nativeQuery = true)
    public List<Holiday> findByGuardByDate(@Param("name") String name, @Param("date") Timestamp date);
}
