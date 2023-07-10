package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.UserEntity;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IUserSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final IUserSpringRepository userSpringRepository;

    public UserEntity findByUserName(String username) {
        Optional<UserEntity> userEntity = userSpringRepository.findById(username);
        return userEntity.orElse(null);
    }
}
