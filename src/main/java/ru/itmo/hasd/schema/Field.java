package ru.itmo.hasd.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static ru.itmo.hasd.schema.FieldType.LIST;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Field {

    private String name;
    private FieldType type;
    private FieldType valueType;
    private String value;

    @Override
    public String toString() {
        if (type == LIST) {
            return "field %s, type %s, value-type %s, values %s;".formatted(name, type.name(), valueType, value);
        }
        return "field %s, type %s, value %s;".formatted(name, type.name(), value);
    }

}
