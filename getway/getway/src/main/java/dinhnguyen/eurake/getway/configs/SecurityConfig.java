package dinhnguyen.eurake.getway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

import dinhnguyen.eurake.getway.securitys.JwtAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = { Directive.CACHE, Directive.COOKIES,
			Directive.STORAGE, Directive.EXECUTION_CONTEXTS };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Entry points
		http.authorizeRequests().antMatchers("/api/auth/login").permitAll()
								.antMatchers("/api/auth/register").permitAll()
								.antMatchers("/product-service/**").hasAuthority("product-service")
								.antMatchers("/order-service/**").hasAuthority("order-service")
								.antMatchers("/logs-service/**").hasAuthority("logs-service")
								.anyRequest().authenticated();
		
		http.logout(logout -> logout.logoutUrl("/api/auth/logout")
				.addLogoutHandler(new SecurityContextLogoutHandler())
				.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE))));
	
				
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Custom authentication provider. This authentication provider will
	 * authenticate the user with the help of @UserdetailsService.
	 * 
	 * @return
	 */
	@Bean
	public CustomAuthenticationProvider authProvider() {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
		return authenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	

}
