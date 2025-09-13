package com.example.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
