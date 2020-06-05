package ru.nik.testtask.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nik.testtask.AbstractTest;
import ru.nik.testtask.model.Human;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomHumanRepositoryImplTest extends AbstractTest {

    @Autowired
    private CustomHumanRepository customHumanRepository;

    @Autowired
    private HumanRepository humanRepository;

    @Test
    void search() {
        String pattern = "ов";
        List<Human> expected = humanRepository.findAllByNameContainingIgnoreCaseOrSecondNameContainingIgnoreCase(pattern, pattern);
        assertNotNull(expected);
        assertEquals(2, expected.size());
        List<Human> actual = customHumanRepository.search(pattern);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        expected.sort(Comparator.comparing(Human::getName));
        actual.sort(Comparator.comparing(Human::getName));
        assertEquals(expected, actual);
    }
}