package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import ru.blss.lab1.domain.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    @Query(value = "update Courier c set c.rating = ?1 where c.id = ?2")
    @Modifying
    @Transactional
    int updateRating(long id, double rating);

}
