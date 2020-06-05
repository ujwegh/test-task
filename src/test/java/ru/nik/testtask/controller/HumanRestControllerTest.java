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
import ru.nik.testtask.dto.HumanDto;
import ru.nik.testtask.model.Car;
import ru.nik.testtask.model.CarType;
import ru.nik.testtask.model.Human;
import ru.nik.testtask.service.HumanService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HumanRestController.class)
class HumanRestControllerTest {

    @MockBean
    private HumanService humanService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Human> humans = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    {
        Human one = new Human("Иван", "Иванов");
        Human two = new Human("Петр", "Петров");
        Human three = new Human("Сергей", "Карама");
        Human four = new Human("Николай", "Выхухоль");
        Car firstCar = new Car("car", CarType.CAR);
        Car secondCar = new Car("truck", CarType.TRUCK);
        one.getCars().add(firstCar);
        two.getCars().add(secondCar);
        humans.addAll(Arrays.asList(one, three, two, four));
        cars.addAll(Arrays.asList(firstCar, secondCar));
    }

    @Test
    void getAll() throws Exception {
        humans.sort(Comparator.comparing(Human::getName));
        Mockito.when(humanService.findAll()).thenReturn(humans);
        mockMvc.perform(MockMvcRequestBuilders.get("/humans"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name", is(humans.get(0).getName())))
                .andExpect(jsonPath("$[0].secondName", is(humans.get(0).getSecondName())));
        verify(humanService, times(1)).findAll();
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void getById() throws Exception {
        humans.sort(Comparator.comparing(Human::getName));
        Mockito.when(humanService.getById(1)).thenReturn(humans.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/humans/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(humans.get(0).getName())))
                .andExpect(jsonPath("$.secondName", is(humans.get(0).getSecondName())))
                .andExpect(jsonPath("$.cars", hasSize(1)));
        verify(humanService, times(1)).getById(1);
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void create() throws Exception {
        HumanDto dto = new HumanDto();
        dto.setName("newMan");
        dto.setSecondName("newSec");
        Human saved = new Human(dto.getName(), dto.getSecondName());
        Mockito.when(humanService.create(any())).thenReturn(saved);
        mockMvc.perform(MockMvcRequestBuilders.post("/humans").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is(saved.getName())))
                .andExpect(jsonPath("$.secondName", is(saved.getSecondName())));

        verify(humanService, times(1)).create(any());
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void update() throws Exception {
        HumanDto dto = new HumanDto();
        dto.setId(1L);
        dto.setName("newMan");
        dto.setSecondName("newSec");
        Human updated = humans.get(0);
        updated.setName(dto.getName());
        updated.setSecondName(dto.getSecondName());
        Mockito.when(humanService.update(any())).thenReturn(updated);
        mockMvc.perform(MockMvcRequestBuilders.put("/humans").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is(updated.getName())))
                .andExpect(jsonPath("$.secondName", is(updated.getSecondName())));

        verify(humanService, times(1)).update(any());
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/humans/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void buyCar() throws Exception {
        humans.sort(Comparator.comparing(Human::getName));
        Mockito.when(humanService.buyCar(1, 1)).thenReturn(humans.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/humans/buy/{humanId}/car/{carId}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(humans.get(0).getName())))
                .andExpect(jsonPath("$.secondName", is(humans.get(0).getSecondName())))
                .andExpect(jsonPath("$.cars", hasSize(1)));
        verify(humanService, times(1)).buyCar(1, 1);
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void sellCar() throws Exception {
        humans.sort(Comparator.comparing(Human::getName));
        Mockito.when(humanService.sellCar(1, 1)).thenReturn(humans.get(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/humans/sell/{humanId}/car/{carId}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(humans.get(1).getName())))
                .andExpect(jsonPath("$.secondName", is(humans.get(1).getSecondName())))
                .andExpect(jsonPath("$.cars", hasSize(0)));
        verify(humanService, times(1)).sellCar(1, 1);
        verifyNoMoreInteractions(humanService);
    }

    @Test
    void searchByString() {

    }
}