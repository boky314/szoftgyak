package glavny.inf.elte.hu.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PrisoncellRepository extends JpaRepository<Prisoncell,Integer> {
    @Override
    List<Prisoncell> findAll();
    @Query(value = "select * from prisoncell where ID <> any(SELECT a.ID FROM (SELECT prisoncell.ID, prisoncell.SPACE, prisoncell.CELL_DESC, count(prisoncell.ID) as CUR_SPACE from prisoncell RIGHT JOIN prisoner ON prisoncell.ID = prisoner.CELL_ID GROUP BY prisoncell.ID) a WHERE  a.SPACE = a.CUR_SPACE)", nativeQuery = true)
    List<Prisoncell> findCellWithFreeSpace();
    @Query(value = "select count(*) from prisoncell where AREA_ID = :id", nativeQuery = true)
    int countCellByAreaId(@Param("id") int id);
    @Query(value = "select sum(space) from prisoncell;", nativeQuery = true)
    int availableSpace();
    @Query(value = "select count(*) from prisoncell", nativeQuery = true)
    public int countPrisonCells();
    @Query(value = "select sum(SPACE) from prisoncell where AREA_ID = :id", nativeQuery = true)
    Integer sumCellByAreaId(@Param("id") int id);

}
