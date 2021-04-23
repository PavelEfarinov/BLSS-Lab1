package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blss.lab1.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
