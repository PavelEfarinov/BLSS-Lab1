package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import ru.blss.lab1.domain.DeliveryCarFlight;

import java.sql.Timestamp;

public interface DeliveryCarRepository extends JpaRepository<DeliveryCarFlight, Long> {

}
