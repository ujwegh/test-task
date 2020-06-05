package ru.nik.testtask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nik.testtask.dto.HumanDto;
import ru.nik.testtask.exceptions.NotFoundException;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.Human;
import ru.nik.testtask.repository.CustomHumanRepository;
import ru.nik.testtask.repository.HumanRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
public class HumanServiceImpl implements HumanService {

    private final HumanRepository humanRepository;
    private final CustomHumanRepository customHumanRepository;
    private final CarService carService;

    @Autowired
    public HumanServiceImpl(HumanRepository humanRepository, CustomHumanRepository customHumanRepository, CarService carService) {
        this.humanRepository = humanRepository;
        this.customHumanRepository = customHumanRepository;
        this.carService = carService;
    }

    @Override
    public Human create(HumanDto dto) {
        log.debug("Create new human: {}", dto);
        Human human = new Human();
        human.setName(dto.getName());
        human.setSecondName(dto.getSecondName());
        return humanRepository.save(human);
    }

    @Override
    public Human update(HumanDto dto) {
        log.debug("Update human: {}", dto);
        Human human = humanRepository.findById(dto.getId()).orElse(null);
        if (human == null) return null;
        human.setName(dto.getName());
        human.setSecondName(dto.getSecondName());
        return humanRepository.save(human);
    }

    @Override
    public void delete(long id) {
        log.debug("Delete human by id: {}", id);
        humanRepository.deleteById(id);
    }

    @Override
    public List<Human> findAll() {
        log.debug("Find all humans");
        return humanRepository.findAll();
    }

    @Override
    public Human getById(long id) {
        log.debug("Find human by id: {}", id);
        return humanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Human> findByStringPattern(String pattern) {
        log.debug("Find all humans by pattern: {}", pattern);
        return customHumanRepository.search(pattern);
    }

    @Override
    public Human buyCar(long humanId, long carId) {
        log.debug("Buy car with id: {} for human with id: {}", carId, humanId);
        Human human = getById(humanId);
        Car car = carService.findById(carId);
        if (human == null) throw new NotFoundException("Human not found");
        if (car == null) throw new NotFoundException("Car not found");
        human.getCars().add(car);
        return humanRepository.save(human);
    }

    @Override
    public Human sellCar(long humanId, long carId) {
        log.debug("Sell car with id: {} for human with id: {}", carId, humanId);
        Human human = getById(humanId);
        Car car = carService.findById(carId);
        if (human == null) throw new NotFoundException("Human not found");
        if (car == null) throw new NotFoundException("Car not found");
        human.getCars().remove(car);
        return humanRepository.save(human);
    }
}
