package com.juan_zubiri.monitoreo.security;

import com.juan_zubiri.monitoreo.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // metodo para ejecutar solicitud HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        // verifico que el encabezado Authorization exista y tenga un token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // debe irse el "Bearer " del encabezado....
            try {
                username = jwtUtil.extractUsername(token); // debe extraerse el token
            } catch (Exception e) {
                logger.error("Error al extraer el nombre de usuario del token", e);
            }
        }

        //   si el usuario existe y el token es válido, lo validamos
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token, username)) {
                User userDetails = (User) customUserDetailsService.loadUserByUsername(username); 
                
                // crear el objeto de autenticación que se agrega al contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // autenticar el usuario 
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // continua con la cadena de filtros..
        chain.doFilter(request, response);
    }
}

