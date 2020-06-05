package ru.nik.testtask.repository;

import ru.nik.testtask.model.Human;

import java.util.List;

public interface CustomHumanRepository {
    List<Human> search(String name);
}
