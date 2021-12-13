package iftm.pedro.aproject.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import iftm.pedro.aproject.entities.User;
import iftm.pedro.aproject.services.UserService;
import org.springframework.http.HttpStatus;
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

    public TokenAuthFilter(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username + "," + password);

        try {
            if(username != null && password != null){
                User user = service.recoverUser(username);
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        password,
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                ));
            }

        } catch (RuntimeException ex){
            System.out.println("MSG:" + ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
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
