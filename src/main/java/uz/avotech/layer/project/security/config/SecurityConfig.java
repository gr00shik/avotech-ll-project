package uz.avotech.layer.project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

	public static final String[] ACTUATOR = {
			"/actuator", "/actuator/**",
			"/swagger-ui.html", "/swagger-ui/index.html", "/swagger-ui/**",
			"/log"
	};

	@Bean
	SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
		http.
				cors().disable().
				authorizeHttpRequests(
						auth -> auth.
								antMatchers(ACTUATOR).permitAll()
//								.antMatchers().hasAnyRole()
//								.antMatchers().hasAnyAuthority()
    			).httpBasic(Customizer.withDefaults());
    			return http.build();
    		}
}
