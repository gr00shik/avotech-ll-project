package uz.avotech.layer.project.security.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uz.avotech.layer.project.security.service.TokenService;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parserBuilder;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static uz.avotech.layer.project.security.SecurityConstants.AUTHORITIES_KEY;
import static uz.avotech.layer.project.security.SecurityConstants.COMPANY;
import static uz.avotech.layer.project.security.SecurityConstants.EXPIRATION_TIME;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private Key key;

    @Value("${app.jwt.secret:null}")
    private String jwtSecret;

    @PostConstruct
    public void init() {
        this.key = hmacShaKeyFor(jwtSecret.getBytes(UTF_8));
    }

    public String create(Authentication auth) {
        var authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(","));

        var expirationDate = new Date(currentTimeMillis() + EXPIRATION_TIME);

        return builder()
                .setIssuer(COMPANY)
                .setSubject(auth.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, HS512)
                .setExpiration(expirationDate)
                .compact();
    }

    public Authentication getAuth(String token) {
        var claims = parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        var authStr = claims.get(AUTHORITIES_KEY).toString().split(",");

        var authorities = stream(authStr)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        var user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    @Override
    public boolean validate(String authToken) {
        try {
            String test = "zxcbzxhczhjxchzchjzgxhjcgzhxjcghjzx87498378237498273497JKHKJHKJHJKHKHjhbjbbbjkdbkbsdkbakbkbaskbdjasdsakjhdksabdbaskbdabdbadkhbashbdbhash8328943274792348723897423dhajkhdsjkdhjkhaskhjd38239472jkdsjksa";
            parserBuilder()
                    .setSigningKey(test)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException exc) {
            log.trace("Invalid JWT token trace.", exc);
            return false;
        }
    }

}
