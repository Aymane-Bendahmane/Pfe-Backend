package com.example.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Entites.Userr;
import com.example.Filters.JwTUtil;
import com.example.Repositories.UserRepository;
import com.example.Service.initDataImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@Api("Basic Authentication endpoints")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })

public class AuthenticationController {
    @Autowired
    initDataImp i;


    @GetMapping(path = "/profile")
    Userr profile(Principal principal) {
        return i.ConsultUserByName(principal.getName());
    }

    @GetMapping(path = "/RefreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception { //Refreshing the access token after verifying the refresh token
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            try {
                String jwt = authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JwTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                Userr userApp = i.ConsultUserByName(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(userApp.getNom())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userApp.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("Refresh token required");
        }
    }
}
