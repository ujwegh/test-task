package ru.nik.testtask.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nik.testtask.AbstractTest;
import ru.nik.testtask.dto.HumanDto;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.Human;
import ru.nik.testtask.repository.CarRepository;
import ru.nik.testtask.repository.HumanRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class HumanServiceImplTest extends AbstractTest {

    @Autowired
    private HumanService service;

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void create() {
        HumanDto dto = new HumanDto();
        dto.setName("new Name");
        dto.setSecondName("new SecondName");
        Human expected = service.create(dto);
        assertNotNull(expected);
        assertEquals(dto.getName(), expected.getName());
        assertEquals(dto.getSecondName(), expected.getSecondName());
        List<Human> all = humanRepository.findAll();
        assertNotNull(all);
        assertEquals(5, all.size());
    }

    @Test
    void update() {
        Human expected = humanRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(expected);
        HumanDto dto = new HumanDto();
        dto.setId(expected.getId());
        dto.setSecondName("new SecondName");
        dto.setName("New Name");
        Human actual = service.update(dto);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Human expected = humanRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(expected);
        service.delete(expected.getId());
        List<Human> actual = humanRepository.findAll();
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    void findAll() {
        List<Human> expected = humanRepository.findAll();
        assertNotNull(expected);
        assertEquals(4, expected.size());
        List<Human> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(4, actual.size());
        expected.sort(Comparator.comparing(Human::getName));
        actual.sort(Comparator.comparing(Human::getName));
        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        Human expected = humanRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(expected);
        Human actual = service.getById(expected.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByStringPattern() {
        String pattern = "ов";
        List<Human> expected = humanRepository.findAllByNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(pattern, pattern);
        assertNotNull(expected);
        assertEquals(2, expected.size());
        List<Human> actual = service.findByStringPattern(pattern);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        expected.sort(Comparator.comparing(Human::getName));
        actual.sort(Comparator.comparing(Human::getName));
        assertEquals(expected, actual);
    }

    @Test
    void buyCar() {
        Human expected = humanRepository.findAll().stream().filter(human -> human.getCars().size() == 0).findFirst().orElse(null);
        assertNotNull(expected);
        Car car = carRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(car);
        expected.getCars().add(car);
        Human actual = service.buyCar(expected.getId(), car.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void sellCar() {
        Human expected = humanRepository.findAll().stream().filter(human -> human.getCars().size() > 0).findFirst().orElse(null);
        assertNotNull(expected);
        Car car = expected.getCars().stream().findFirst().orElse(null);
        assertNotNull(car);
        expected.getCars().remove(car);
        Human actual = service.sellCar(expected.getId(), car.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}