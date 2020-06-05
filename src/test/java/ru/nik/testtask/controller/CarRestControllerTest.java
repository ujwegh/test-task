package ru.nik.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nik.testtask.dto.CarDto;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.CarType;
import ru.nik.testtask.service.CarService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CarRestController.class)
class CarRestControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Car> cars = new ArrayList<>();

    {
        Car firstCar = new Car("car", CarType.CAR);
        Car secondCar = new Car("truck", CarType.TRUCK);
        cars.addAll(Arrays.asList(firstCar, secondCar));
    }

    @Test
    void create() throws Exception {
        CarDto dto = new CarDto();
        dto.setName("new moto");
        dto.setType(CarType.MOTO);
        Car saved = new Car(dto.getName(), dto.getType());
        Mockito.when(carService.create(any())).thenReturn(saved);
        mockMvc.perform(MockMvcRequestBuilders.post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is(saved.getName())))
                .andExpect(jsonPath("$.type", is(saved.getType().name())));

        verify(carService, times(1)).create(any());
        verifyNoMoreInteractions(carService);
    }

    @Test
    void getAll() throws Exception {
        cars.sort(Comparator.comparing(Car::getName));
        Mockito.when(carService.findAll()).thenReturn(cars);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(cars.get(0).getName())))
                .andExpect(jsonPath("$[0].type", is(cars.get(0).getType().name())));
        verify(carService, times(1)).findAll();
        verifyNoMoreInteractions(carService);
    }

    @Test
    void getById() throws Exception {
        cars.sort(Comparator.comparing(Car::getName));
        Mockito.when(carService.findById(1)).thenReturn(cars.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(cars.get(0).getName())))
                .andExpect(jsonPath("$.type", is(cars.get(0).getType().name())));
        verify(carService, times(1)).findById(1);
        verifyNoMoreInteractions(carService);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}", 1))
                .andExpect(status().isOk());
    }
}