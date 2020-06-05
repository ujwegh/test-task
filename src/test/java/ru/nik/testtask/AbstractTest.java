package ru.nik.testtask;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.CarType;
import ru.nik.testtask.model.Human;
import ru.nik.testtask.repository.CarRepository;
import ru.nik.testtask.repository.HumanRepository;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @author nik_aleks
 * @version 1.0
 */
@Transactional
@SpringBootTest
public class AbstractTest {

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void init() {
        Human one = humanRepository.save(new Human("Иван", "Иванов"));
        Human two = humanRepository.save(new Human("Петр", "Петров"));
        Human three = humanRepository.save(new Human("Сергей", "Карама"));
        Human four = humanRepository.save(new Human("Николай", "Выхухоль"));
        Car firstCar = carRepository.save(new Car("car", CarType.CAR));
        Car secondCar = carRepository.save(new Car("truck", CarType.TRUCK));
        one.getCars().add(firstCar);
        two.getCars().add(secondCar);
        humanRepository.saveAll(Arrays.asList(one, two));
    }

}
