package com.bbva.wshomebanking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Paso 1. Validar que sea un Header Authorization válido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer" )) {
            filterChain.doFilter(request,response);
            return;
        }

        // Paso 2. Validar que el JWT sea válido
        //Al hacer el split, tenemos el prefijo "Bearer" en el índice 0 y el jwt como tal en el índice 1
        //Método trim para asegurarnos que no tenga espacios ni antes ni después
        String jwt = authHeader.split(" ") [1].trim();

        //mandamos como argumento a través del método isValid el jwt obtenido anteriormente
        if (!jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request,response);
            return;
        }

        // Paso 3. Cargar el usuario del UserDetailsService
        String username = jwtUtil.getUsernameByToken(jwt);

        User user = (User) userDetailsService.loadUserByUsername(username);

        // Paso 4. Cargar al usuario en el contexto de seguridad,
        // indicando al contexto del Spring Security Filter chain que esta request es válida.
        //Al estar trabajando con jwt y ya no con nombre de usuario y contraseña,
        //en vez de trabajar con el AuthenticationManager, vamos a trabajar con UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword(),user.getAuthorities());


        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //Aquí ocurre la magia porque, antes de decirle a Spring Security FilterChain que siga con sus filtros,
        // estamos cargando la autenticación en el contexto de seguridad,
        //lo que resolverá positivamente en términos de seguridad esta petición
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println(authenticationToken);

        // le decimos a Spring Security FilterChain que siga con sus filtros
        // pero ya con todo lo referido a la autenticación cargado en el contexto de seguridad
        filterChain.doFilter(request,response);

    }
}
