package ru.nik.testtask.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nik.testtask.AbstractTest;
import ru.nik.testtask.dto.CarDto;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.CarType;
import ru.nik.testtask.repository.CarRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceImplTest extends AbstractTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void findAll() {
        List<Car> expected = carRepository.findAll();
        assertNotNull(expected);
        assertEquals(2, expected.size());
        List<Car> actual = carService.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        expected.sort(Comparator.comparing(Car::getName));
        actual.sort(Comparator.comparing(Car::getName));
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Car expected = carRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(expected);
        Car actual = carService.findById(expected.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void create() {
        CarDto carDto = new CarDto();
        carDto.setName("moto");
        carDto.setType(CarType.MOTO);
        Car actual = carService.create(carDto);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(carDto.getName(), actual.getName());
        assertEquals(carDto.getType(), actual.getType());
    }

    @Test
    void delete() {
        Car expected = carRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(expected);
        assertNotNull(expected.getId());
        carService.delete(expected.getId());
        List<Car> actual = carRepository.findAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertNotEquals(expected.getId(), actual.get(0).getId());
    }
}