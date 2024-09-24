package ru.itmo.hasd.deserializers;

import ru.itmo.hasd.schema.FieldType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class CustomDeserializer<T> implements Deserializer<T> {

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
                return clazz.getDeclaredConstructor().newInstance();
            }

            if (line.startsWith("field ")) {
                var tokens = line.split("[,;]");
                var field = clazz.getDeclaredField(tokens[0].trim().substring("field ".length()));
                field.setAccessible(true);
                setFieldValue(
                        field, result,
                        FieldType.fromName(tokens[1].trim().substring("type ".length())),
                        tokens[2].trim().substring("value ".length()));
                return result;
            }

            throw new IllegalArgumentException("Неизвестный компонент схемы: " + line);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "Произошла ошибка при десериализации поля %s связанная с отсутствием поля в искомом классе %s"
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
            switch (type) {
                case INT -> field.set(object, Integer.parseInt(value));
                case LONG -> field.set(object, Long.parseLong(value));
                case FLOAT -> field.set(object, Float.parseFloat(value));
                case DOUBLE -> field.set(object, Double.parseDouble(value));
                case BOOL -> field.set(object, Boolean.parseBoolean(value));
                case CHAR -> {
                    if (value.length() > 1) {
                        throw new IllegalArgumentException();
                    }

                    field.set(object, value.charAt(0));
                }
                default -> field.set(object, value);
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
