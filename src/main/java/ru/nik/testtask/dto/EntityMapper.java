package ru.nik.testtask.dto;

import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.Human;

import java.util.stream.Collectors;

/**
 * @author nik_aleks
 * @version 1.0
 */

public class EntityMapper {

    private EntityMapper() {
    }

    public static HumanDto toHumanDto(Human from) {
        if (from == null) return null;
        HumanDto to = new HumanDto();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setSecondName(from.getSecondName());
        to.setCars(from.getCars().stream()
                .map(EntityMapper::toCarDto)
                .collect(Collectors.toSet())
        );
        return to;
    }

    public static CarDto toCarDto(Car from) {
        if (from == null) return null;
        CarDto to = new CarDto();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setType(from.getType());
        return to;
    }
}
