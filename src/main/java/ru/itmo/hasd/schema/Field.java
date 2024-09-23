package ru.itmo.hasd.schema;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public record Field(String name, FieldType type, String value) {

    @Override
    public String toString() {
        return "field %s, type %s, value %s;".formatted(name, type.name(), value);
    }

}
