package swt6.orm.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long     id;
  private String   name;
  @ManyToMany
  private Set<Employee> members = new HashSet<>();

  public Long getId() {
    return id;
  }

  public Project() {  
  }
  
  public Project(String name) {
    this.name = name;  
  }

  public Set<Employee> getMembers() {
    return members;
  }

  public void setMembers(Set<Employee> members) {
    this.members = members;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
