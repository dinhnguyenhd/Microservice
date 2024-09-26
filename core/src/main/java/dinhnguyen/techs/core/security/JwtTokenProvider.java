package dinhnguyen.techs.core.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import dinhnguyen.techs.core.configs.CoreUerDetails;
import dinhnguyen.techs.core.configs.CoreUserDetailsService;
import dinhnguyen.techs.core.form.users.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private String jwtSecret = "Y29uZHVvbmd4dWFlbWRp";

	// 15 min = 900,000 ms
	private int accessToken = 900000;

	@Autowired
	private CoreUserDetailsService coreUserDetailsService;

	public String accessToken(Authentication authentication) {

		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken) authentication;
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessToken);

		return Jwts.builder().setSubject(userInfo.getName()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String refreshToken(Authentication authentication) {
		UUID refreshToken = UUID.randomUUID();
		return refreshToken.toString();

	}

	public String getEmailFromJWT(String token) {

		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.get("sub").toString();

	}

	public boolean isExpired(String token) {
		boolean flag = false;
		try {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			flag = claims.getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			flag = true;
		}
		return flag;

	}

	public boolean isValidToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			return false;
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			return false;
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			return false;
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			return false;
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			return false;
		}

	}

	public JwtToken generateToken(Authentication authentication) {
		String accessToken = accessToken(authentication);
		String refreshToken = refreshToken(authentication);
		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken) authentication;
		Set<String> roles = new HashSet<>();
		for (GrantedAuthority at : userInfo.getAuthorities()) {
			roles.add(at.getAuthority());
		}
		return new JwtToken(userInfo.getName(), accessToken, refreshToken, roles, false, false);

	}

	public UsernamePasswordAuthenticationToken getAuthentication(String email) {
		CoreUerDetails userDetails = coreUserDetailsService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		return authentication;
	}

}
