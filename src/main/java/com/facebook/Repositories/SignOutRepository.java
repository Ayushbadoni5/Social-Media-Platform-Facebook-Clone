package com.facebook.Repositories;

import com.facebook.Entities.SignOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SignOutRepository extends JpaRepository<SignOut, UUID> {

    boolean existsByToken(String token);
}
