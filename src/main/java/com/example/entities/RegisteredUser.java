package com.example.entities;

import com.example.entities.enums.UserRole;
import com.example.entities.enums.converters.UserRoleConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "registered_user")
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    private String email;

    private String password;

    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    private Set<Card> cards = new HashSet<>();

}
