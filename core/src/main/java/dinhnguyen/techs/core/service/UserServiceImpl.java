package dinhnguyen.techs.core.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import dinhnguyen.techs.core.configs.CoreAuthenticationProvider;
import dinhnguyen.techs.core.entity.Jwtoken;
import dinhnguyen.techs.core.entity.Role;
import dinhnguyen.techs.core.entity.User;
import dinhnguyen.techs.core.exceptions.InvalidUserException;
import dinhnguyen.techs.core.form.users.JwtToken;
import dinhnguyen.techs.core.form.users.LoginForm;
import dinhnguyen.techs.core.form.users.RegisterForm;
import dinhnguyen.techs.core.form.users.SearchForm;
import dinhnguyen.techs.core.form.users.JwtForm;
import dinhnguyen.techs.core.repository.JwtRepository;
import dinhnguyen.techs.core.repository.RoleRepository;
import dinhnguyen.techs.core.repository.UserRepository;
import dinhnguyen.techs.core.security.JwtTokenProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	CoreAuthenticationProvider coreAuthenticationProvider;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtRepository jwtRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	public dinhnguyen.techs.core.form.users.UserForm register(RegisterForm form) {

		form.setPassword(this.passwordEncoder.encode(form.getPassword()));
		User user = this.modelMapper.map(form, User.class);
		this.userRepository.save(user);
		// Save role to DB:
		Set<Role> set = new HashSet<>();
		if (form.getRole().size() > 0) {
			for (String role : form.getRole()) {
				Role myrole = new Role();
				myrole.setRoleName(role);
				myrole.setUser(user);
				set.add(myrole);
				this.roleRepository.save(myrole);
			}
		}
		user.setRoles(set);
		dinhnguyen.techs.core.form.users.UserForm userRp = modelMapper.map(user,
				dinhnguyen.techs.core.form.users.UserForm.class);
		return userRp;

	}

	@Override
	public JwtToken login(LoginForm user) {

		JwtToken jwt;
		try {
			UsernamePasswordAuthenticationToken userInfo = (new UsernamePasswordAuthenticationToken(user,
					user.getPassword()));
			Authentication authentication = coreAuthenticationProvider.authenticate(userInfo);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwt = jwtTokenProvider.generateToken(authentication);
			Jwtoken token = this.modelMapper.map(jwt, Jwtoken.class);

			this.jwtRepository.save(token);
		} catch (Exception e) {
			throw new InvalidUserException("Invalid user name or password");
		}

		return jwt;
	}

	@Override
	public JwtToken refreshToken(JwtToken oldToken) {
		System.out.println(oldToken.toString());
		JwtToken newToken = null;

		Optional<Jwtoken> opt = this.jwtRepository.getTokenByRefreshToken(oldToken.getRefreshToken().trim());
		if (opt.isPresent()) {
			Jwtoken dbToken = opt.get();
			// Compare token:
			if (dbToken.getAccessToken().equals(oldToken.getAccessToken().trim())) {

				UsernamePasswordAuthenticationToken authentication = jwtTokenProvider
						.getAuthentication(oldToken.getEmail());
				newToken = jwtTokenProvider.generateToken(authentication);
				// Create new token and save it to DB:
				Jwtoken newdbToken = new Jwtoken();
				newdbToken.setAccessToken(newToken.getAccessToken());
				newdbToken.setRefreshToken(newToken.getRefreshToken());
				newdbToken.setEmail(newToken.getEmail());
				System.out.println("New token " + newdbToken.toString());
				this.jwtRepository.save(newdbToken);
			}
		} else {
			throw new InvalidUserException("Fake old refresh token");
		}

		return newToken;
	}

	@Override
	public void signout(JwtForm token) {
		Jwtoken dbToken = this.jwtRepository.getTokenByEmail(token.getEmail());
		if (dbToken != null) {
			dbToken.setExpired(true);
			// Thu hồi token:
			dbToken.setRevoked(true);
			jwtRepository.save(dbToken);
		}
		SecurityContextHolder.clearContext();

	}

	@Override
	public Page<User> search(SearchForm form) {
		Pageable pageable = PageRequest.of(form.getCurrentPage(), form.getPageSize());
		Page<User> page = this.userRepository.search(form.getName(), pageable);
		return page;
	}

}
