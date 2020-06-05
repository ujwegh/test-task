package ru.nik.testtask.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nik.testtask.dto.EntityMapper;
import ru.nik.testtask.dto.HumanDto;
import ru.nik.testtask.service.HumanService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nik.testtask.util.ValidationUtil.checkAllIdNotNull;
import static ru.nik.testtask.util.ValidationUtil.checkIdNotNull;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/humans")
public class HumanRestController {

    private final HumanService humanService;

    @Autowired
    public HumanRestController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping
    public List<HumanDto> getAll() {
        log.info("Get all humans");
        return humanService.findAll().stream()
                .map(EntityMapper::toHumanDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{humanId}")
    public HumanDto getById(@PathVariable("humanId") Long id) {
        log.info("Get human by id: {}", id);
        checkIdNotNull(id);
        return EntityMapper.toHumanDto(humanService.getById(id));
    }

    @PostMapping
    public HumanDto create(@RequestBody @Valid HumanDto humanDto) {
        log.info("Create new human: {}", humanDto);
        return EntityMapper.toHumanDto(humanService.create(humanDto));
    }

    @PutMapping
    public HumanDto update(@RequestBody @Valid HumanDto humanDto) {
        checkIdNotNull(humanDto.getId());
        log.info("Update human with id: {}", humanDto.getId());
        return EntityMapper.toHumanDto(humanService.update(humanDto));
    }

    @DeleteMapping("/{humanId}")
    public void delete(@PathVariable("humanId") Long humanId) {
        log.info("Delete human by id: {}", humanId);
        checkIdNotNull(humanId);
        humanService.delete(humanId);
    }

    @GetMapping("/buy/{humanId}/car/{carId}")
    public HumanDto buyCar(@PathVariable("humanId") Long humanId, @PathVariable("carId") Long carId) {
        log.info("Buy car with id: {} for human with id: {}", carId, humanId);
        checkAllIdNotNull(humanId, carId);
        return EntityMapper.toHumanDto(humanService.buyCar(humanId, carId));
    }

    @GetMapping("/sell/{humanId}/car/{carId}")
    public HumanDto sellCar(@PathVariable("humanId") Long humanId, @PathVariable("carId") Long carId) {
        log.info("Sell car with id: {} for human with id: {}", carId, humanId);
        checkAllIdNotNull(humanId, carId);
        return EntityMapper.toHumanDto(humanService.sellCar(humanId, carId));
    }

    @GetMapping("/search")
    public List<HumanDto> searchByString(@RequestParam String pattern) {
        log.info("Search humans by pattern: {}", pattern);
        return humanService.findByStringPattern(pattern).stream()
                .map(EntityMapper::toHumanDto)
                .collect(Collectors.toList());
    }

}
