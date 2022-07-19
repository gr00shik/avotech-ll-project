package uz.avotech.layer.project.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

	public static final String[] ACTUATOR = {
			"/actuator", "/actuator/**",
			"/swagger-ui.html", "/swagger-ui/index.html", "/swagger-ui/**",
			"/log"
	};

	public static final String[] PUBLIC_API = {
			"api/v1/**"
	};

	@Bean
	SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
		http.
				sessionManagement().sessionCreationPolicy(STATELESS).
				and().
				cors().disable().
				csrf().disable().
				authorizeHttpRequests(auth ->
						auth.antMatchers(ACTUATOR).permitAll().
						antMatchers(PUBLIC_API).permitAll()
				).httpBasic(Customizer.withDefaults());
    			return http.build();
    		}
}
