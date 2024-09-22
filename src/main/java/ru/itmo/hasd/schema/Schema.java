package ru.itmo.hasd.schema;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Schema {

    private String name;

    private List<Field> fields;

}
