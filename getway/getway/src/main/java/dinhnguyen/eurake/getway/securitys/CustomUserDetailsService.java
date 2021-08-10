package dinhnguyen.eurake.getway.securitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dinhnguyen.eurake.getway.entitys.User;
import dinhnguyen.eurake.getway.exceptions.NotFoundException;
import dinhnguyen.eurake.getway.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found [email: " + email + "]"));

		return UserPrincipal.create(user);
	}

	@Transactional
	@Cacheable("loadUserById")
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found [id: " + id + "]"));

		return UserPrincipal.create(user);
	}
}
