package glavny.inf.elte.hu.data;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import java.util.stream.Collectors;

@Entity
@Table(name="password")
public class Password implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password pass = (Password) o;
        return  Objects.equals(username, pass.username) &&
                Objects.equals(password, pass.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username,password);
    }
   
}
