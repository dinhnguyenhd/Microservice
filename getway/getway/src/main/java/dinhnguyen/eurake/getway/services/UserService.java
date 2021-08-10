package dinhnguyen.eurake.getway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dinhnguyen.eurake.getway.entitys.User;
import dinhnguyen.eurake.getway.forms.UserSummary;
import dinhnguyen.eurake.getway.repository.UserRepository;
import dinhnguyen.eurake.getway.securitys.UserPrincipal;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
		return UserSummary.builder().id(userPrincipal.getId()).email(userPrincipal.getEmail())
				.name(userPrincipal.getName()).build();
	}

	public List<User> list() {
		return this.userRepository.findAll();
	}
}
