package dinhnguyen.techs.mongodb.controlller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.mongodb.entitys.User;
import dinhnguyen.techs.mongodb.forms.SearchUserForms;
import dinhnguyen.techs.mongodb.forms.UserForm;
import dinhnguyen.techs.mongodb.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController implements CommonController<UserForm, User> {

	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	@GetMapping("/list")
	public ResponseEntity<List<User>> list() {
		List<User> list = this.userService.list();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@Override
	@PostMapping("/add")
	public ResponseEntity<User> save(@Valid @RequestBody UserForm form) {
		User user = this.modelMapper.map(form, User.class);
		user = this.userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Override
	@PutMapping("/update")
	public ResponseEntity<User> update(@Valid @RequestBody UserForm form) {
		User user = this.modelMapper.map(form, User.class);
		user = this.userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Override
	@GetMapping("/find/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") String id) {
		User user = this.userService.findByIds(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		this.userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/search/complex")
	public ResponseEntity<List<User>> complexSearch(@RequestBody SearchUserForms form) {
		List<User> list = this.userService.complexSearch(form);

		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@GetMapping("/findby/{email}")
	public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email) {
		User user = this.userService.findbyEmail(email.trim());
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}
}
