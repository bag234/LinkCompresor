package org.mrbag.LinkCompresor.Layer;

import java.io.IOException;

import org.mrbag.LinkCompresor.LinkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TraficCheckFilter extends OncePerRequestFilter {

    private LinkRepository repo;

    final String ip_header;

    public TraficCheckFilter(LinkRepository repo, @Value("${app.nginx: X-Real-IP}") String ip_header){
        this.repo = repo;
        this.ip_header = ip_header;
    }

    private static String toKey(String raw){ 
        if (raw.length() != 6 || raw.lastIndexOf('/') != 0)
            return null; 
        return raw.substring(1);
    }

    private String check(HttpServletRequest request){
        
        String ip = request.getHeader(ip_header);
        if(ip != null)
            return ip;

        return request.getRemoteAddr();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String key = toKey(request.getRequestURI());
            if(key != null && repo.getLink(key).isLayer())
                repo.layer(key, check(request));
        }
        finally{
             filterChain.doFilter(request, response);
        }
    }

}
