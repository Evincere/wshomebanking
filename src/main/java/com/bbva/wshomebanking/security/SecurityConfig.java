package com.bbva.wshomebanking.security;

import com.bbva.wshomebanking.utilities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/user/create").permitAll()    // dejamos fuera del alcance de la aplicación la gestion de los usuarios administradores
            .antMatchers("/client/create").permitAll()
            .antMatchers("/client/myprofile").hasRole(Roles.CLIENT)
            .antMatchers("/client/update").hasAnyRole(Roles.CLIENT, Roles.ADMIN)
            .antMatchers("/client/**").hasRole(Roles.ADMIN)
            .antMatchers("/account/myaccounts").hasRole(Roles.CLIENT)
            .antMatchers("/account/**").hasRole(Roles.ADMIN)
            .antMatchers("/clientaccount/**").hasRole(Roles.ADMIN)
            .antMatchers("/transaction/deposit").hasRole(Roles.CLIENT)
            .antMatchers("/transaction/transfer").hasRole(Roles.CLIENT)
            .antMatchers("/transaction/extraction").hasRole(Roles.CLIENT)
            .antMatchers("/transaction/mytransactions").hasRole(Roles.CLIENT)
            .antMatchers("/transaction/**").hasRole(Roles.ADMIN)
            .antMatchers("/auth/login").permitAll()
            .anyRequest().authenticated()
        .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/login")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout")
            .permitAll();

        http.csrf().disable();
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
    @Bean
    //Método para poder obtener nuestro AuthenticationManager y utilizarlo en nuestro AuthController
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }

}
