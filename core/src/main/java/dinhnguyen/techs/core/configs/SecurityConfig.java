package dinhnguyen.techs.core.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dinhnguyen.techs.core.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CoreAuthenticationProvider coreAuthenticationProvider;

	@Autowired
	CoreAuthenticationManager coreAuthenticationManager;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeRequests(auth -> auth.antMatchers("/resources/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/api-docs/**", "/swagger-ui/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/authen/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/jwt/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/users/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/assets/**").permitAll())
				.authorizeRequests(auth -> auth.antMatchers("/order-service/**").hasAuthority("order-service"))
				.authorizeRequests(auth -> auth.antMatchers("/logs-service/**").hasAuthority("logs-service"))
				.authenticationProvider(coreAuthenticationProvider)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	AuthenticationManager authenticationManagerBean() throws Exception {
		return this.coreAuthenticationManager;
	}

	@Bean
	CoreAuthenticationProvider authProvider() {
		return this.coreAuthenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
