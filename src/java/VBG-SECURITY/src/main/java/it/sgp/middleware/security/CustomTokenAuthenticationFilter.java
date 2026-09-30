package it.sgp.middleware.security;

import java.io.IOException;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.sgp.middleware.security.service.LoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(CustomTokenAuthenticationFilter.class);

	private LoginService loginservice;
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER_PREFIX = "Bearer "; //va bene con lo spazio

	private static final ObjectMapper objectMapper;

	static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
	
	public CustomTokenAuthenticationFilter(LoginService loginservice) {
		super();
		this.loginservice = loginservice;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String header = request.getHeader(AUTHORIZATION);
			if (header != null && header.startsWith(BEARER_PREFIX)) {
	            String token = header.substring(7);

	            if(!loginservice.checkToken(token).isValid()) {
	            	throw new Exception("Errore di autenticazione: il Bearer token passato non risulta valido");
	            }
	            
	        }else {
	        	throw new Exception("Errore di autenticazione: non risulta nessun Bearer token passato correttamente");
	        }
			
		}catch(Exception e) {
			
			log.error(e.getMessage(),e);		
			
			ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()));
	        problem.setTitle("Errore di autenticazione");
	        problem.setDetail(e.getMessage());
	        problem.setType(URI.create("https://api.soluzioni.it/problems/invalid-token"));
	        problem.setInstance(URI.create(request.getRequestURI()));

	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.setContentType("application/json");
	        objectMapper.writeValue(response.getWriter(), problem);
	        
	        return;
			
		}
		
		
		filterChain.doFilter(request, response);
		
		
	}
	
}
