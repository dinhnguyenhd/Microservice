package dinhnguyen.techs.core.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	private String email;

	private String password;

	private boolean enabled;

	private boolean expired;

	@OneToMany(mappedBy = "user")
	private Set<Role> roles;

	private boolean timeExpired;

	private boolean isLocked;

	private boolean pwdExpired;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", enabled="
				+ enabled + ", expired=" + expired + ", timeExpired=" + timeExpired + ", isLocked=" + isLocked
				+ ", pwdExpired=" + pwdExpired + "]";
	}

}