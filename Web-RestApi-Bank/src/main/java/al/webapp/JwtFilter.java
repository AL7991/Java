package al.webapp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter implements javax.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String header = httpServletRequest.getHeader("Authorization");

        if(httpServletRequest == null || !header.startsWith("Bearer ")) {

            throw new ServletException("Wrong or Empty header");
        }else{
            try{
                String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("ZG(9n.oY?s=]s,Q").parseClaimsJws(token).getBody();
            }catch (Exception e){
                throw new ServletException("Wrong key");
            }

        }

        chain.doFilter(request,response);


    }
}
