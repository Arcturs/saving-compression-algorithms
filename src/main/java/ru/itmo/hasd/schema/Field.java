package ru.itmo.hasd.schema;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public record Field(

        String name,
        FieldType type,

        // TODO: think
        String value

) {

}
