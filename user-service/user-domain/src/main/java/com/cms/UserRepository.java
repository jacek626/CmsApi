package com.cms;


import com.cms.agregate.Login;
import com.cms.agregate.User;
import org.jmolecules.ddd.annotation.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findById(User.UserId id);

    List<User> findByLogin(Login login);

}
