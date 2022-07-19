package uz.avotech.layer.project.security.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String create(Authentication auth);
    Authentication getAuth(String token);
    boolean validate(String authToken);

}
