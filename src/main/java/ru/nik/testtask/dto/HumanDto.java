package ru.nik.testtask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class HumanDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String secondName;

    private Set<CarDto> cars = new HashSet<>();
}
