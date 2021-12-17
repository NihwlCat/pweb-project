package iftm.pedro.aproject.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import iftm.pedro.aproject.entities.User;
import iftm.pedro.aproject.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class TokenAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final long TOKEN_EXP = 720000L;
    public static final String SECRET = "SECRET";

    private final UserService service;

    private final AuthenticationManager authenticationManager;

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendRedirect("/login");
    }

    public TokenAuthFilter(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        username = username != null ? username : "";
        username = username.trim();
        String password = this.obtainPassword(request);
        password = password != null ? password : "";

        final User user = service.recoverUser(username);

        String role = user != null ? user.getRole() : "COMMON";

        System.out.println("PASSWORD: " + password + ", USERNAME: " + username);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(role)
        )));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();


        String role = authResult.getAuthorities().stream().findFirst().get().getAuthority();
        String token = JWT.create().
                withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP))
                .withClaim("role",role)
                .sign(Algorithm.HMAC512(SECRET));

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge((int)TOKEN_EXP);
        response.addCookie(cookie);
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
