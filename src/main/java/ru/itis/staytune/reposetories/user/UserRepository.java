package ru.itis.staytune.reposetories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.staytune.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findAll();

    @Query(nativeQuery = true, value = "select * from account join concert_user cu on account.id = cu.user_id where concert_id = :id")
    List<User> findByConcertId(@Param("id") Long userId);
}
