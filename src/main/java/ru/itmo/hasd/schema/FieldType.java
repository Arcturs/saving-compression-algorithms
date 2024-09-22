package ru.itmo.hasd.schema;

import java.lang.reflect.Field;

public enum FieldType {

    SCHEMA,
    INT,
    LONG,
    FLOAT,
    STRING,
    CHAR;

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

        return SCHEMA;
    }

}
