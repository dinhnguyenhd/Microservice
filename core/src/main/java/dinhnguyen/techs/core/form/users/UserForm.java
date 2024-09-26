package dinhnguyen.techs.core.form.users;

import java.util.Set;

import dinhnguyen.techs.core.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean enabled;
	private boolean tokenExpired;
	private Set<Role> roles;

}
