package com.example.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {// Filter interrupt every single http requests to te server and verify Tokens

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/RefreshToken")) { //Blocking the verification of Authorization Header on this header
            System.out.println("*********************refreshing the token*************************");
            filterChain.doFilter(request, response);//
        } else {
            String authorizationToken = request.getHeader(JwTUtil.AUTH_HEADER);
            if (authorizationToken != null && authorizationToken.startsWith(JwTUtil.PREFIX)) {//Verifying access Token
                try {
                    String jwt = authorizationToken.substring(JwTUtil.PREFIX.length());
                    Algorithm algorithm = Algorithm.HMAC256(JwTUtil.SECRET);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                    for (String role : roles) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(role));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);//Load User in the context
                    filterChain.doFilter(request, response);//Go next
                } catch (Exception e) {
                    response.setHeader("Error Message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }
}
