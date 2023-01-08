package com.cms;

import com.cms.agregate.Login;
import com.cms.agregate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserApplicationRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(User.UserId id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> findByLogin(Login login) {
        return userJpaRepository.findByLogin(login);
    }
}
