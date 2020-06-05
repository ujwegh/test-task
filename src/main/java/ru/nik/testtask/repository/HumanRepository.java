package ru.nik.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nik.testtask.model.Human;

import java.util.List;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {
    // длинновато, но как же просто
    List<Human> findAllByNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(String pattern1, String pattern2);
}
