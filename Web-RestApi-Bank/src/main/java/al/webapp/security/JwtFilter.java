package al.webapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends BasicAuthenticationFilter {
    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String requestURI = request.getRequestURI();

        if(requestURI.equals("/api/logIn") || requestURI.equals("/api/register")){
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            throw new ServletException("Wrong or Empty header");
        }

        UsernamePasswordAuthenticationToken authRequest = getAuthenticationByToken(header);

        SecurityContextHolder.getContext().setAuthentication(authRequest);

        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) throws ServletException {

        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("ZG(9n.oY?s=]s,Q")
                    .parseClaimsJws(header.substring(7));

            String userName = claimsJws.getBody().get("name").toString();
            String role = claimsJws.getBody().get("role").toString();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userName, null, Collections.singleton(new SimpleGrantedAuthority(role)));

            return usernamePasswordAuthenticationToken;
        }catch (Exception e){
            throw new ServletException("Wrong key");
        }

    }


}