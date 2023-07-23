package com.bbva.wshomebanking.presentation.controllers;

import com.bbva.wshomebanking.presentation.request.login.LoginRequest;
import com.bbva.wshomebanking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    //este endpoint debe permitirse en los antMatchers sino también se devolverá un 401
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {

        //Creamos un UsernamePasswordAuthenticationToken a partir del username y password que nos llegó en el request body del login
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        //Luego, creamos un objeto de autenticación que como valor tiene el llamado al método authenticate del AuthenticationManager
        //Internamente este método va a Authentication provider y éste va a User Details Service (en nuestro caso, al CustomUserDetailsService).
        //El Auth Provider recupera ese usuario y verifica la contraseña en la bdd con la recibida en el Login request
        //Si es correcta, sigue el código, sino se corta el flujo del código y devuelve un 401
        Authentication authentication = authenticationManager.authenticate(login);

        //Imprimimos un booleano indicando si ese usuario se autenticó o no
        //Si llega a esta línea es porque el usuario se autenticó exitosamente
        System.out.println(authentication.isAuthenticated());

        //Imprimimos el usuario como tal que está en el contexto de seguridad de Spring
        System.out.println(authentication.getPrincipal());

        //creamos el jwt que le vamos a enviar al usuario autenticado
        String jwt = jwtUtil.create(loginRequest.getUsername());

        //asignamos ese jwt como respuesta a nuestra solicitudN
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwt).build();
    }
}
