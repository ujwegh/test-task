package ru.nik.testtask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.nik.testtask.model.CarType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class CarDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private CarType type;
}
