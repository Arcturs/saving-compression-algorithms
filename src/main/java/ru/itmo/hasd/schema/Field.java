package ru.itmo.hasd.schema;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Field {

    private String name;

    private FieldType type;

    // TODO: think
    private String value;

}
