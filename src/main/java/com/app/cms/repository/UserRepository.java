package com.app.cms.repository;

import com.app.cms.entity.User;
import com.app.cms.valueobject.user.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, UserRepositoryMethods {
    // @Query("select count(u)>0 from User u where u.login = :login")
    boolean existsByLogin(Login login);

    // @Query("select count(u)>0 from User u where u.login = :login")
    //  boolean existsByLogin(String login);

    User findByLoginValue(String login);

    @Query("select count(u)>0 from User u where u.login = :login and u.id = :id")
    boolean existsByLoginAndIdNot(Login login, Long id);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param(value = "id") long id, @Param(value = "password") char[] password);

}
