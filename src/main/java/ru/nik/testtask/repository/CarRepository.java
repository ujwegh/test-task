package ru.nik.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nik.testtask.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
