package dinhnguyen.techs.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dinhnguyen.techs.core.entity.Jwtoken;
import dinhnguyen.techs.core.repository.JwtRepository;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
	private JwtRepository jwtRepository;

	@Override
	public Jwtoken findTokenById(Long tokenId) {
		Optional<Jwtoken> token = jwtRepository.findById(tokenId);
		return token.get() != null ? token.get() : null;
	}

	@Override
	public Jwtoken saveToken(Jwtoken token) {
		return this.jwtRepository.save(token);
	}

}
