package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE login=?1 AND password=?2", nativeQuery = true)
    User findByLoginAndPassword(String login, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET password=?3 WHERE id=?1", nativeQuery = true)
    void updatePassword(long id, String password);

    int countByLogin(String login);

    List<User> findAllByOrderByIdDesc();
}
