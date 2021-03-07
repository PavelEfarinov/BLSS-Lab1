package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE login=?1 AND passwordSha=encode(digest((CONCAT('1be3db47a7684152', ?1, ?2)), 'sha1'), 'hex')", nativeQuery = true)
    User findByLoginAndPassword(String login, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET passwordSha=encode(digest((CONCAT('1be3db47a7684152', ?2, ?3)), 'sha1'), 'hex') WHERE id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String login, String password);

    int countByLogin(String login);

    List<User> findAllByOrderByIdDesc();
}
