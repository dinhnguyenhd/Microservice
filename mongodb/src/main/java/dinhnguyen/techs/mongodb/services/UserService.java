package dinhnguyen.techs.mongodb.services;

import java.util.List;

import dinhnguyen.techs.mongodb.entitys.User;
import dinhnguyen.techs.mongodb.forms.SearchUserForms;

public interface UserService extends CommonActions<User> {

	public User findbyEmail(String email);

	public List<User> complexSearch(SearchUserForms form);

}
