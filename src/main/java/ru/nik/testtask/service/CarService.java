package ru.nik.testtask.service;

import ru.nik.testtask.dto.CarDto;
import ru.nik.testtask.model.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();
    Car findById(long id);
    Car create(CarDto dto);
    void delete(long id);
}
