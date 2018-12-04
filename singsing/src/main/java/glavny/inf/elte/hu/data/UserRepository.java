package glavny.inf.elte.hu.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import hu.elte.szgy.data.Beteg;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,String>  {
	
	@Query("select u from User u where u.username = :userName")
	public User findUserByName(@Param("userName") String userName);
	
	@Modifying
	@Query(value = "UPDATE user SET password = :password,  registration =CURRENT_TIMESTAMP() WHERE username = :username ", nativeQuery = true)
	public void changePassword(@Param("username") String username,@Param("password") String password);
}
