package com.bbva.wshomebanking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static String secret_key = "b@nc0=bbV@";

    //definimos el algoritmo a utilizar para crear el jwt con nuestra palabra clave
    private static Algorithm algorithm = Algorithm.HMAC256(secret_key);


    //Método para crear un JWT para un usuario autenticado
    public String create (String username) {
        return JWT.create()
                //el asunto siempre será el usuario en cuestión
                .withSubject(username)
                //quien crea el jwt
                .withIssuer("banco-bbva")
                //fecha de creación
                .withIssuedAt(new Date())
                //fecha de expiración
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                //firma del token a través del método que recibe un algoritmo
                //este método devolverá un String que en su contenido tendrá el JWT
                .sign(algorithm);
    }

    //Método para validar un jwt
    public boolean isValid (String jwt) {
        try{
            //Definimos que esta instancia requiere este algoritmo con el que construimos nuestro JWT
            JWT.require(algorithm)
                    .build()
                    //Este método verifica si el jwt que recibe mi método es válido o no
                    .verify(jwt);
            return true;
            //capturamos la excepción que lanzaría el verify si el jwt no es válido
        } catch (JWTVerificationException e) {
            return false;
        }

    }

    //Método para obtener el usuario al cual pertenece el jwt que se está validando
    public String getUsernameByToken (String jwt) {
        return JWT.require(algorithm)
                .build()
                .verify(jwt)
                //obtenemos el asunto del jwt en cuestión que, como dijimos antes,
                // siempre va a ser el usuario autenticado
                .getSubject();
    }
}
