package ru.itis.staytune.reposetories.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.staytune.models.concert.Concert;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    @Query(nativeQuery = true, value = "select * from concert join concert_user cu on concert.id = cu.concert_id where cu.user_id = :id")
    List<Concert> findByUserId(@Param("id") Long id);

    List<Concert> findAll();

    Optional<Concert> findById(Long id);
}
