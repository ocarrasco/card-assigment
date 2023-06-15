package com.example.repositories;

import com.example.entities.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<RegisteredUser, Integer> {

    Optional<RegisteredUser> findByEmail(String email);

    @Query(
            "SELECT CASE WHEN (count(*) > 0) THEN TRUE ELSE FALSE END FROM RegisteredUser u " +
                    "WHERE u.id = ?1 and u.role = com.example.entities.enums.UserRole.ADMIN"
    )
    boolean isAdmin(Integer registeredUserId);

}
