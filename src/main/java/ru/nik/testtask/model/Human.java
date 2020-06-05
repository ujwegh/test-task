package ru.nik.testtask.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Data
@Entity
@Table(name = "humans")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Human extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Car> cars = new HashSet<>();

    public Human(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }

}
