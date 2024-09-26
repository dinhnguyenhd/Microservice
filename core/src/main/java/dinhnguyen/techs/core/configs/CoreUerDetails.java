package dinhnguyen.techs.core.configs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dinhnguyen.techs.core.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreUerDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public CoreUerDetails(Long id, String name, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static CoreUerDetails create(User user) {

		List<GrantedAuthority> authorities = new ArrayList<>();
		if (user != null && user.getRoles() != null) {
			authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
					.collect(Collectors.toList());
		}
		return new CoreUerDetails(user.getId(), user.getName(), user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CoreUerDetails that = (CoreUerDetails) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}