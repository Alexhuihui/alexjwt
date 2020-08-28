package top.alexmmd.alexjwt.security;

/**
 * @author 汪永晖
 */

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.alexmmd.alexjwt.model.ApplicationUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static top.alexmmd.alexjwt.constants.SecurityConstants.*;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser applicationUser = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(applicationUser.getUsername(),
                    applicationUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 从Authentication对象中取出User信息
        User user = ((User) auth.getPrincipal());

        // 将权限列表转换为一个数组列表，方便转换成JSON
        // user.getAuthorities()返回的是user对象中的Collection<GrantedAuthority>，authority.getAuthority()则返回权限字符串名称，最后得到一个字符串列表
        List<String> roles = user.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());

        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", roles)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp)
                .compact();
//        res.addHeader("token", token);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json; charset=utf-8");
        PrintWriter out = res.getWriter();
        JSONObject response = new JSONObject();
        response.put("code", "100");
        response.put("msg", "login success");
        response.put("data", token);
        out.append(response.toString());

    }

    /**
     * Default behaviour for unsuccessful authentication.
     *
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.warn("No failure URL set, sending 401 Unauthorized error");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject res = new JSONObject();
        res.put("code", "401");
        res.put("msg", "认证失败");
        out.append(res.toString());
    }
}
