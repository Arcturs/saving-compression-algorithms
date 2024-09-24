package ru.itmo.hasd.schema;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum FieldType {

    SCHEMA,
    INT,
    LONG,
    DOUBLE,
    FLOAT,
    STRING,
    CHAR,
    BOOL,
    LIST,
    SET,
    MAP;

    public static FieldType fromField(Field field) {
        if (field.getType() == Integer.class || field.getType() == int.class) {
            return INT;
        }

        if (field.getType() == Long.class || field.getType() == long.class) {
            return LONG;
        }

        if (field.getType() == Double.class || field.getType() == double.class) {
            return DOUBLE;
        }

        if (field.getType() == Float.class || field.getType() == float.class) {
            return FLOAT;
        }

        if (field.getType() == String.class) {
            return STRING;
        }

        if (field.getType() == Character.class || field.getType() == char.class) {
            return CHAR;
        }

        if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            return BOOL;
        }

        if (field.getType() == List.class || field.getType() == Arrays.class) {
            return LIST;
        }

        if (field.getType() == Set.class) {
            return SET;
        }

        if (field.getType() == Map.class) {
            return MAP;
        }

        return SCHEMA;
    }

    public static FieldType fromName(String name) {
        return Arrays.stream(FieldType.values())
                .filter(type -> type.name().equals(name))
                .findFirst()
                .orElse(SCHEMA);
    }

}
