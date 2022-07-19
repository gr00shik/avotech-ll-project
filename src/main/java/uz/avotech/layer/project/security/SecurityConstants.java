package uz.avotech.layer.project.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/api/authenticate";
    public static final String COMPANY = "Avotech.uz";

    public static final String AUTHORITIES_KEY = "auth";
}
