package uz.avotech.layer.project.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.avotech.layer.project.security.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.util.StringUtils.hasText;
import static uz.avotech.layer.project.security.SecurityConstants.AUTHORIZATION_HEADER;
import static uz.avotech.layer.project.security.SecurityConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtTokenGeneratedFilter extends OncePerRequestFilter {

    private final TokenService service;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        var authentication = getContext().getAuthentication();
        if ( isNull(authentication) ) {
            var token = getTokenFromRequest(req);
            if (StringUtils.hasText(token) && service.validate(token)) {
                var auth = service.getAuth(token);
                getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, resp);
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        if (hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
