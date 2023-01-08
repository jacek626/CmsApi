package com.cms;

import com.cms.agregate.Login;
import com.cms.agregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, User.UserId> {
    List<User> findByLogin(Login login);
}
