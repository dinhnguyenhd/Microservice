package dinhnguyen.techs.core.service;

import java.util.List;

import dinhnguyen.techs.core.entity.Role;

public interface RoleService {

	public List<String> getRoleByEmailId(String email);

	public Role save(Role role);

}