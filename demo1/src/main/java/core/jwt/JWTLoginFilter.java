package core.jwt;

import com.alibaba.fastjson.JSON;
import core.Model.Toekn;
import core.Model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public JWTLoginFilter(AuthenticationManager  authenticationManager,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder =  bCryptPasswordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            try {
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getName(),
                                user.getPassword(),
                                new ArrayList<>())
                );
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        res.addHeader("Authorization", "Bearer " + token);
    }
}
