package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> , JpaSpecificationExecutor<AppUser> {

    boolean existsByEmail(String email);

    @Query("select u from AppUser u join fetch u.roles where u.email = :email")
    Optional<AppUser> findByEmailWithRoles(String email);

    @Query(value = "select * from users where id = :id" , nativeQuery = true)
    Optional<AppUser> findByIdIncludeDeleted(@Param("id") Long id);

    Optional<AppUser> findByIdAndDeletedFalse(Long id);

}
