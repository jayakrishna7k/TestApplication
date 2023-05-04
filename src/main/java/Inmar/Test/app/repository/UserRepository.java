package Inmar.Test.app.repository;

import Inmar.Test.app.jpa.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    @Query(value = "select user from AppUser user WHERE user.username = :username")
    AppUser findByUserName(@Param("username") String username);
}
