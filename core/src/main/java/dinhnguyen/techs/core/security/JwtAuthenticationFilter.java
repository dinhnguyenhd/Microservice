package dinhnguyen.techs.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import dinhnguyen.techs.core.configs.CoreUerDetails;
import dinhnguyen.techs.core.configs.CoreUserDetailsService;

@Component
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CoreUserDetailsService coreUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(" No chay qua :  order-service " + request.getRequestURL());
		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.isValidToken(jwt)) {
				String email = tokenProvider.getEmailFromJWT(jwt);
				CoreUerDetails userDetails = coreUserDetailsService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String jwt = "";
		if (request.getHeader("Authorization") != null) {
			if (request.getHeader("Authorization").length() > 7) {
				String bearerToken = request.getHeader("Authorization");
				if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
					jwt = bearerToken.substring(7, bearerToken.length());

				}
			}

		}
		return jwt;
	}

}
