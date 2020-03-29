/*
 * This class defines Entity associated with the table in Database that holds data about users
 */


package alexlab.MessageStoringFullStackApp.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {
	
	//***********Attributes***************//	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column (nullable=false)
	@NotEmpty
	private String name;
	
	@Column (nullable=false, unique=true)
	@NotEmpty
	@Email(message="{error.invalid_email")	
	private String email;
	
	@Column(nullable=false)
	@NotEmpty
	@Size(min=8)
	private String password;
	
	
	//***********Association***************//
	// Define many-to-many relationship via user-role table with the object-type-Role as non-owning side
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="user_role",
			joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
	
	// List to store the roles assigned to the user
	private List<Role> roles;
	
	
	//********Getters&Setters**********//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	

}
