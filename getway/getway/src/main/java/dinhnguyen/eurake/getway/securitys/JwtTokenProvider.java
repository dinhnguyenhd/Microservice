package dinhnguyen.eurake.getway.securitys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import dinhnguyen.eurake.getway.forms.JwtTokenResponse;
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

	// @Value("${app.jwtSecret}")
	private String jwtSecret = "Y29uZHVvbmd4dWFlbWRp";

	// @Value("${app.jwtExpirationInMs}")
	private int accessToken = 36000000;

	private int refreshToken = 36000000;

	public String accessToken(Authentication authentication) {

		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken) authentication;
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessToken);

		return Jwts.builder().setSubject(userInfo.getName()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String refreshToken(Authentication authentication) {

		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken) authentication;
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + refreshToken);
		return Jwts.builder().setSubject(userInfo.getName()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getEmailFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return (String) claims.get("sub");
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

	public JwtTokenResponse generateToken(Authentication authentication) {
		String accessTk = accessToken(authentication);
		String refreshTk = refreshToken(authentication);
		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken) authentication;
		String username = userInfo.getName();
		Set<String> roles = new HashSet<>();
		for (GrantedAuthority at : userInfo.getAuthorities()) {
			roles.add(at.getAuthority());
		}
		return new JwtTokenResponse(username, accessTk, refreshTk, roles);

	}

}
