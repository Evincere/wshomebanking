package com.bbva.wshomebanking.security;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Client> client = clientRepository.findByPersonalId(username);
        if(!client.isPresent())
            throw new UsernameNotFoundException(ErrorCodes.INVALID_LOGIN);

        // Obtener los roles y autorizaciones del usuario
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(Roles.CLIENT));

        // Crear un objeto UserDetails con los detalles del usuario
        return new org.springframework.security.core.userdetails.User(
                client.get().getPersonalId(),
                client.get().getPassword(),
                authorities
        );
    }
}
