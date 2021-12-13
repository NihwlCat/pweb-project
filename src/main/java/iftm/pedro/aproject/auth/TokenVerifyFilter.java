package iftm.pedro.aproject.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenVerifyFilter extends BasicAuthenticationFilter {

    public TokenVerifyFilter(AuthenticationManager manager) {
        super(manager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException, ServletException, IOException {

        Cookie cookie = WebUtils.getCookie(request, "token");

        if (cookie == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(cookie.getValue());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        try {
            DecodedJWT tokenDecoded = JWT.require(Algorithm.HMAC512("SECRET"))
                    .build()
                    .verify(token);

            return new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(tokenDecoded.getClaim("role").asString())));

        } catch (JWTVerificationException ex){
            SecurityContextHolder.clearContext();
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
