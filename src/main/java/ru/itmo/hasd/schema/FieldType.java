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
    FLOAT,
    STRING,
    CHAR,
    LIST,
    SET,
    MAP;

    public static FieldType fromField(Field field) {
        if (field.getType() == Integer.class) {
            return INT;
        }

        if (field.getType() == Long.class) {
            return LONG;
        }

        if (field.getType() == Float.class) {
            return FLOAT;
        }

        if (field.getType() == String.class) {
            return STRING;
        }

        if (field.getType() == Character.class) {
            return CHAR;
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

}
