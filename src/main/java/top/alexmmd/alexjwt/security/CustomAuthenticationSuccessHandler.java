package top.alexmmd.alexjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.*;

import static top.alexmmd.alexjwt.constants.SecurityConstants.EXPIRATION_TIME;
import static top.alexmmd.alexjwt.constants.SecurityConstants.KEY;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        logger.info("登录成功");

        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
//        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
        Map<String, String> claims = new HashMap<>();
        claims.put("mobile", ((User) auth.getPrincipal()).getUsername());
        StringBuffer roles = new StringBuffer();
        Iterator<? extends GrantedAuthority> iterator = auth.getAuthorities().iterator();
        while (iterator.hasNext()) {
            roles.append(iterator.next().getAuthority());
            if (iterator.hasNext()) {
                roles.append(",");
            }
        }
        claims.put("role", roles.toString());

        String token = Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS512).setExpiration(exp).compact();
        response.addHeader("token", token);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(auth));
    }
}
