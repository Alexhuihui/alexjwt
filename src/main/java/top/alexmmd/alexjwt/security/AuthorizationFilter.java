package top.alexmmd.alexjwt.security;

/**
 * @author 汪永晖
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static top.alexmmd.alexjwt.constants.SecurityConstants.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_NAME);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authRequest = convert(request);
        if (authRequest == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authRequest);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            return null;
        }
        // 尝试验证JWT
        // 解析第一部，获取解析后的前两部分的拼合对象
        Jws<Claims> jws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes())).parseClaimsJws(token);
        // 从claims中获取放入的用户名
        String username = jws.getBody().getSubject();
        // 从role字符串数组转换成权限对象
        ArrayList<String> roleStrings = (ArrayList<String>) jws.getBody().get("role");
        List<GrantedAuthority> authorities = roleStrings.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
