package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> , JpaSpecificationExecutor<AppUser> {
//    @EntityGraph(attributePaths = {"role", "role.permissions"})
//    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select u from AppUser u join fetch u.roles where u.email = :email")
    Optional<AppUser> findByEmailWithRoles(String email);
}
