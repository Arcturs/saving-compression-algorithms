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
import static ru.itmo.hasd.schema.FieldType.DOUBLE;
import static ru.itmo.hasd.schema.FieldType.FLOAT;
import static ru.itmo.hasd.schema.FieldType.INT;
import static ru.itmo.hasd.schema.FieldType.LIST;
import static ru.itmo.hasd.schema.FieldType.LONG;
import static ru.itmo.hasd.schema.FieldType.MAP;
import static ru.itmo.hasd.schema.FieldType.SCHEMA;
import static ru.itmo.hasd.schema.FieldType.SET;
import static ru.itmo.hasd.schema.FieldType.STRING;

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
                return clazz.getDeclaredConstructor(String.class).newInstance(className);
            }

            if (line.startsWith("field ")) {
                var tokens = line.split("[,;]");
                var field = clazz.getDeclaredField(tokens[0].trim().substring("field ".length()));
                field.setAccessible(true);
                setFieldValue(
                        field, result,
                        FieldType.fromName(tokens[1].trim().substring("type ".length())),
                        tokens[2].trim().substring("value ".length(), tokens[2].trim().length() - 2));
                return result;
            }

            throw new IllegalArgumentException("Неизвестный компонент схемы: " + line);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "Произошла ошибка при десериализации поля %s, связанная с отсутствием поля в искомом классе %s"
                            .formatted(line, clazz.getSimpleName()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Поле %s недоступно для редактирования".formatted(line));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(
                    "Поля %s не существует в искомом классе %s"
                            .formatted(line, clazz.getSimpleName()));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при десериализации", e);
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
            if (type == DOUBLE) {
                field.setDouble(object, Double.parseDouble(value));
            }
            if (type == FLOAT) {
                field.setFloat(object, Float.parseFloat(value));
            }
            if (type == LONG) {
                field.setLong(object, Long.parseLong(value));
            }
            if (type == CHAR) {
                if (value.length() > 1) {
                    throw new IllegalArgumentException();
                }

                field.setChar(object, value.charAt(0));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Поле %s недоступно для редактирования".formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к типу %s"
                            .formatted(field.getName(), type.name()));
        }
        catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при десериализации", e);
        }
    }

}
