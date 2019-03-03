package book.store.config.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	Logger log = Logger.getLogger(AuthenticationFilter.class);
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if (!request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
			}
	
			String requestBody = IOUtils.toString(request.getReader());
			LoginRequest loginRequest = new ObjectMapper().readValue(requestBody, LoginRequest.class);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch(IOException e) {
            log.error(e.getMessage(), e);
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
	}
}

@Setter
@Getter
@NoArgsConstructor
class LoginRequest {
	private String username;
	private String password;
}
