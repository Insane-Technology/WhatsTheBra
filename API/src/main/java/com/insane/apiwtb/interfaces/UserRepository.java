package com.insane.apiwtb.interfaces;

import com.insane.apiwtb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
