package ru.itmo.hasd.lab1.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Person {

    private long id;
    private Integer age;
    private Double salary;
    private float cash;
    private String name;
    private Character gender;
    private boolean isEmployed;

    private List<String> hobbies;
    private Set<Integer> childrenIds;

    private Map<String, Long> luggage;

}
