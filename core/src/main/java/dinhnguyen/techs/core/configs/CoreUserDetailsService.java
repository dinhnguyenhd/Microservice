package dinhnguyen.techs.core.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dinhnguyen.techs.core.entity.User;
import dinhnguyen.techs.core.exceptions.RecordNotFoundException;
import dinhnguyen.techs.core.repository.UserRepository;

@Service
public class CoreUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public CoreUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public CoreUerDetails loadUserByUsername(String email) {
		User account = null;
		try {
			Optional<User> user = this.userRepository.getUserByEmail(email);
			if (user.isPresent())
				account = user.get();
		} catch (Exception e) {
			throw new RecordNotFoundException(e.getMessage());
		}
		return CoreUerDetails.create(account);

	}

	@Transactional
	@Cacheable("loadUserById")
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("User not found [id: " + id + "]"));

		return CoreUerDetails.create(user);
	}

}
