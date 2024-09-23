package ru.itmo.hasd.deserializers;

import ru.itmo.hasd.schema.FieldType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.EnumSet;

import static ru.itmo.hasd.schema.FieldType.CHAR;
import static ru.itmo.hasd.schema.FieldType.FLOAT;
import static ru.itmo.hasd.schema.FieldType.INT;
import static ru.itmo.hasd.schema.FieldType.LIST;
import static ru.itmo.hasd.schema.FieldType.LONG;
import static ru.itmo.hasd.schema.FieldType.MAP;
import static ru.itmo.hasd.schema.FieldType.SCHEMA;
import static ru.itmo.hasd.schema.FieldType.SET;
import static ru.itmo.hasd.schema.FieldType.STRING;

// TODO: handle exceptions properly
public class CustomDeserializer<T> implements Deserializer<T> {

    private static final EnumSet<FieldType> NOT_OPERATED_FIELD_TYPES = EnumSet.of(STRING, SCHEMA, LIST, SET, MAP);

    @Override
    public T deserialize(Class<T> clazz, File file) throws IOException {
        try (var in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)))) {

            var line = in.readLine();
            T value = null;
            while (line != null) {
                value = processLine(line, clazz, value);
                line = in.readLine();
            }

            return value;
        }
    }

    private T processLine(String line, Class<T> clazz, T result) {
        try {
            if (line.startsWith("class ")) {
                var className = line.substring("class ".length(), line.length() - 2);
                try {
                    return clazz.getDeclaredConstructor(String.class).newInstance(className);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (line.startsWith("field ")) {
                var tokens = line.split("[,;]");
                var field = clazz.getField(tokens[0].trim().substring("field ".length()));
                field.setAccessible(true);
                setFieldValue(
                        field, result,
                        FieldType.fromName(tokens[1].trim().substring("type ".length())),
                        tokens[2].trim().substring("value ".length(), tokens[2].trim().length() - 2));
                return result;
            }

            // TODO: throw exception

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(Field field, T object, FieldType type, String value) {
        try {
            if (NOT_OPERATED_FIELD_TYPES.contains(type)) {
                field.set(object, value);
                return;
            }

            if (type == INT) {
                field.setInt(object, Integer.parseInt(value));
            }
            if (type == FLOAT) {
                field.setFloat(object, Float.parseFloat(value));
            }
            if (type == LONG) {
                field.setLong(object, Long.parseLong(value));
            }
            if (type == CHAR) {
                // TODO: handle if string size > 1
                field.setChar(object, value.charAt(0));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
