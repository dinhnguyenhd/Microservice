package dinhnguyen.techs.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dinhnguyen.techs.core.entity.Role;
import dinhnguyen.techs.core.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<String> getRoleByEmailId(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role save(Role role) {
		
		return roleRepository.save(role);
	}

}
