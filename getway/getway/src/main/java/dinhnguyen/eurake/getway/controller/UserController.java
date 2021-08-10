package dinhnguyen.eurake.getway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.eurake.getway.entitys.User;
import dinhnguyen.eurake.getway.forms.UserSummary;
import dinhnguyen.eurake.getway.securitys.CurrentUser;
import dinhnguyen.eurake.getway.securitys.UserPrincipal;
import dinhnguyen.eurake.getway.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/myinfo")
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		return userService.getCurrentUser(currentUser);
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> list() {
		return userService.list();
	}

}
