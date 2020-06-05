package ru.nik.testtask.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nik.testtask.dto.CarDto;
import ru.nik.testtask.dto.EntityMapper;
import ru.nik.testtask.service.CarService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nik.testtask.util.ValidationUtil.checkIdNotNull;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/cars")
public class CarRestController {

    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public CarDto create(@RequestBody @Valid CarDto dto) {
        log.info("Create new car: {}", dto);
        return EntityMapper.toCarDto(carService.create(dto));
    }

    @GetMapping
    public List<CarDto> getAll() {
        log.info("Get all cars");
        return carService.findAll().stream()
                .map(EntityMapper::toCarDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{carId}")
    public CarDto getById(@PathVariable("carId") Long id) {
        log.info("Get car by id: {}", id);
        checkIdNotNull(id);
        return EntityMapper.toCarDto(carService.findById(id));
    }

    @DeleteMapping("/{carId}")
    public void delete(@PathVariable("carId") Long id) {
        log.info("Delete car by id: {}", id);
        checkIdNotNull(id);
        carService.delete(id);
    }
}
