package book.store.config.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(POST, "/users").permitAll()
			.antMatchers(GET, "/books").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(this::logoutSuccessHandler)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint);
	}
	
	@Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", POST.name()));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }
	
	private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		response.setStatus(HttpStatus.OK.value());
	}

	private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
	private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		response.setStatus(HttpStatus.OK.value());
	}
}
