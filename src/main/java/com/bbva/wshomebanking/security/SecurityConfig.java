package com.bbva.wshomebanking.security;

import com.bbva.wshomebanking.utilities.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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
            .antMatchers("/transaction/**").hasRole(Roles.CLIENT)
            .anyRequest().authenticated()
        .and()
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
