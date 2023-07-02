package com.java.springboot.demo.repository;

import com.java.springboot.demo.entity.UserFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserFlag, Long> {

    Optional<UserFlag> findByIdAndType(Long id, String type);
}
