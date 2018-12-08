package glavny.inf.elte.hu.data;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public interface PasswordRepository extends JpaRepository<Password,String> {

	@Query(value = "select password from Password where username = :username ", nativeQuery = true)
	ArrayList<String> findPasswordByUserName(@Param("username") String username);
	
	@Modifying 
	@Query(value = "INSERT INTO password(username,password) VALUES(:username, :password) ", nativeQuery = true)
	void addNewPassword(@Param("username") String username,@Param("password") String password);
	
	@Modifying 
	@Query(value = "DELETE FROM password WHERE username=:username AND password =:password ", nativeQuery = true)
	void removePassword(@Param("username") String username,@Param("password") String password);
  
}
