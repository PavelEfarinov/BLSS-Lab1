package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import ru.blss.lab1.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findAllByUsernameAndPassword(String username, String password);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.password = ?2 where u.id = ?1")
    void updatePassword(long id, String password);

    int countByUsername(String username);

    User findByUsername(String username);

    List<User> findAllByOrderByIdDesc();
}
