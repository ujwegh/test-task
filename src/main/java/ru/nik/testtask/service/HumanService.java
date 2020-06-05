package ru.nik.testtask.service;

import ru.nik.testtask.dto.HumanDto;
import ru.nik.testtask.model.Human;

import java.util.List;

public interface HumanService {
    Human create(HumanDto dto);
    Human update(HumanDto dto);
    void delete(long id);
    List<Human> findAll();
    Human getById(long id);
    List<Human> findByStringPattern(String pattern);
    Human buyCar(long humanId, long carId);
    Human sellCar(long humanId, long carId);
}
