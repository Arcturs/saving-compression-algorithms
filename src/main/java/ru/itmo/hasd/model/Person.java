package ru.itmo.hasd.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

    private Long id;
    private Integer age;
    private Double salary;
    private Float cash;
    private String name;
    private Character gender;

}
