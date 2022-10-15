package dinhnguyen.eurake.getway.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dinhnguyen.eurake.getway.configs.CustomAuthenticationProvider;
import dinhnguyen.eurake.getway.entitys.Role;
import dinhnguyen.eurake.getway.entitys.User;
import dinhnguyen.eurake.getway.exceptions.ConflictException;
import dinhnguyen.eurake.getway.forms.JwtTokenResponse;
import dinhnguyen.eurake.getway.forms.LoginRequest;
import dinhnguyen.eurake.getway.forms.SignUpRequest;
import dinhnguyen.eurake.getway.repository.RoleRepository;
import dinhnguyen.eurake.getway.repository.UserRepository;
import dinhnguyen.eurake.getway.securitys.JwtAuthenticationFilter;
import dinhnguyen.eurake.getway.securitys.JwtTokenProvider;

@Service
public class AuthService {

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider tokenProvider;

	public JwtTokenResponse authenticateUser(LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken user = (new UsernamePasswordAuthenticationToken(loginRequest,
				loginRequest.getPassword()));

		Authentication authentication = customAuthenticationProvider.authenticate(user);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		JwtTokenResponse jwt = tokenProvider.generateToken(authentication);
		return jwt;
	}

	// Xứ lí lại cái add role - Add multiple role
	public Long registerUser(SignUpRequest signUpRequest) {
		Long id;
		User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ConflictException("Email [email: " + signUpRequest.getEmail() + "] is already taken");
		} else {

			Optional<Role> userRole = roleRepository.roleAlreadyExit(signUpRequest.getRoleName());
			// Case : Role exits in DB
			if (userRole.isPresent()) {
				user.setRoles(Collections.singleton(userRole.get()));
			}
			// Add new role to DB:
			else {
				Role role = new Role();
				role.setName(signUpRequest.getRoleName());
				Set<Role> set = new HashSet<>();
				set.add(role);
				user.setRoles(set);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			this.userRepository.save(user);
			id = user.getId();
		}
		logger.info("Successfully registered user with [email: {}]", user.getEmail());
		return id;
	}
}
