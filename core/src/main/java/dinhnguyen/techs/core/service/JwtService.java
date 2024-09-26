package dinhnguyen.techs.core.service;

import dinhnguyen.techs.core.entity.Jwtoken;

public interface JwtService {

	public Jwtoken findTokenById(Long tokenId);

	public Jwtoken saveToken(Jwtoken token);
}
