package ru.itmo.hasd.lab1.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static ru.itmo.hasd.lab1.schema.FieldType.LIST;
import static ru.itmo.hasd.lab1.schema.FieldType.MAP;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Field {

    private String name;
    private FieldType type;
    private FieldType keyType;
    private FieldType valueType;
    private String value;

    @Override
    public String toString() {
        if (type == LIST) {
            return "field %s, type %s, value-type %s, values %s;".formatted(name, type.name(), valueType, value);
        }
        if (type == MAP) {
            return "field %s, type %s, key-type %s, value-type %s, values %s;"
                    .formatted(name, type.name(), keyType, valueType, value);
        }
        return "field %s, type %s, value %s;".formatted(name, type.name(), value);
    }

}
