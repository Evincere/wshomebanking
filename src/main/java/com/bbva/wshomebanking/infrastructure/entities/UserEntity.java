package com.bbva.wshomebanking.infrastructure.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class UserEntity {

    @Id
    private String username;
    private String password;
    private String role;
}
