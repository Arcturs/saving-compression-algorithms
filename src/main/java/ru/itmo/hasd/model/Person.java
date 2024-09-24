package ru.itmo.hasd.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

    private long id;
    private int age;
    private double salary;
    private float cash;
    private String name;
    private char gender;
    private boolean isEmployed;

}
