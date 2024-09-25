package ru.itmo.hasd.schema;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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
    MAP;

    public static FieldType fromClassType(Class<?> clazz) {
        if (clazz == Integer.class || clazz == int.class) {
            return INT;
        }

        if (clazz == Long.class || clazz == long.class) {
            return LONG;
        }

        if (clazz == Double.class || clazz == double.class) {
            return DOUBLE;
        }

        if (clazz == Float.class || clazz == float.class) {
            return FLOAT;
        }

        if (clazz == String.class) {
            return STRING;
        }

        if (clazz == Character.class || clazz == char.class) {
            return CHAR;
        }

        if (clazz == Boolean.class || clazz == boolean.class) {
            return BOOL;
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return LIST;
        }

        if (Map.class.isAssignableFrom(clazz)) {
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
