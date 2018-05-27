package glavny.inf.elte.hu.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PrisoncellRepository extends JpaRepository<Prisoncell,Integer> {
    @Override
    List<Prisoncell> findAll();
    @Query(value = "select * from prisoncell where ID <> any(SELECT a.ID FROM (SELECT prisoncell.ID, prisoncell.SPACE, prisoncell.CELL_DESC, count(prisoncell.ID) as CUR_SPACE from prisoncell RIGHT JOIN prisoner ON prisoncell.ID = prisoner.CELL_ID GROUP BY prisoncell.ID) a WHERE  a.SPACE = a.CUR_SPACE)", nativeQuery = true)
    List<Prisoncell> findCellWithFreeSpace();
}
