package ru.nik.testtask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nik.testtask.dto.CarDto;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.repository.CarRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository repository;

    @Autowired
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> findAll() {
        log.debug("Find all cars");
        return repository.findAll();
    }

    @Override
    public Car findById(long id) {
        log.debug("Find car by id: {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Car create(CarDto dto) {
        log.debug("Create new car: {}", dto);
        return repository.save(new Car(dto.getName(), dto.getType()));
    }

    @Override
    public void delete(long id) {
        log.debug("Delete car by id: {}", id);
        repository.deleteById(id);
    }
}
