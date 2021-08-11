package dinhnguyen.eurake.getway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dinhnguyen.eurake.getway.securitys.CustomUserDetailsService;
import dinhnguyen.eurake.getway.securitys.JwtAuthenticationEntryPoint;
import dinhnguyen.eurake.getway.securitys.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**", "/h2/**" };

	private CustomUserDetailsService customUserDetailsService;
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService,
			JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.customUserDetailsService = customUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	public SecurityConfig(boolean disableDefaults, CustomUserDetailsService customUserDetailsService,
			JwtAuthenticationEntryPoint unauthorizedHandler) {
		super(disableDefaults);
		this.customUserDetailsService = customUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().headers().frameOptions().disable().and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/api/auth/**").permitAll()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers("/product-service/**").hasAuthority("product-service")
				.antMatchers("/order-service/**").hasAuthority("order-service")
				.antMatchers("/logs-service/**").hasAuthority("logs-service")
				.anyRequest().authenticated();

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
