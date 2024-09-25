package ru.itmo.hasd.deserializers;

import ru.itmo.hasd.schema.FieldType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static ru.itmo.hasd.schema.FieldType.LIST;
import static ru.itmo.hasd.schema.FieldType.MAP;

// TODO: prettify
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

            var tokens = line.split("[,;]");
            var field = clazz.getDeclaredField(tokens[0].trim().substring("field ".length()));
            field.setAccessible(true);
            var fieldType = FieldType.fromName(tokens[1].trim().substring("type ".length()));

            if (line.contains("key-type ")) {
                if (fieldType != MAP) {
                    throw new IllegalArgumentException("Указан неверный тип поля для словаря для десериализации");
                }
                setFieldMapValues(
                        field, result,
                        FieldType.fromName(tokens[2].trim().substring("key-type ".length())),
                        FieldType.fromName(tokens[3].trim().substring("value-type ".length())),
                        tokens[4].trim().substring("values [".length(), tokens[4].length() - 2));
                return result;
            }

            if (line.contains("value-type ")) {
                if (fieldType != LIST) {
                    throw new IllegalArgumentException("Указан неверный тип поля для коллекции для десериализации");
                }
                setFieldValues(
                        field, result,
                        FieldType.fromName(tokens[2].trim().substring("value-type ".length())),
                        tokens[3].trim().substring("values [".length(), tokens[3].length() - 2));
                return result;
            }

            if (line.startsWith("field ")) {
                setFieldValue(
                        field, result, fieldType,
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
            var parsedValue = parseValueFromString(type, value);
            field.set(object, parsedValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Поле %s недоступно для редактирования".formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к типу %s"
                            .formatted(field.getName(), type.name()));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при десериализации", e);
        }
    }

    private void setFieldValues(Field field, T object, FieldType valueFieldType, String value) {
        try {
            var elements = value.split(" \\|\\| ");
            Collection<Object> deserializedElements = new ArrayList<>(elements.length);
            for (var element : elements) {
                deserializedElements.add(parseValueFromString(valueFieldType, element));
            }
            field.set(object, getCollection(field.getType(), deserializedElements));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Поле %s недоступно для редактирования".formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к коллекции типа %s"
                            .formatted(field.getName(), valueFieldType.name()));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при десериализации", e);
        }
    }

    private void setFieldMapValues(
            Field field, T object,
            FieldType keyFieldType,
            FieldType valueFieldType,
            String value) {

        try {
            var elements = value.split(" \\|\\| ");
            Map<Object, Object> deserializedMap = new HashMap<>(elements.length);
            for (var element : elements) {
                var entry = element.split("--");
                deserializedMap.put(
                        parseValueFromString(keyFieldType, entry[0]),
                        parseValueFromString(valueFieldType, entry[1]));
            }
            field.set(object, deserializedMap);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Поле %s недоступно для редактирования".formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к коллекции типа %s"
                            .formatted(field.getName(), valueFieldType.name()));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при десериализации", e);
        }
    }

    private static Object parseValueFromString (FieldType fieldType, String value) {
        return switch (fieldType) {
            case INT -> Integer.parseInt(value);
            case LONG -> Long.parseLong(value);
            case FLOAT -> Float.parseFloat(value);
            case DOUBLE -> Double.parseDouble(value);
            case BOOL -> Boolean.parseBoolean(value);
            case CHAR -> {
                if (value.length() > 1) {
                    throw new IllegalArgumentException();
                }
                yield value.charAt(0);
            }
            default -> value;
        };
    }

    private static Collection<Object> getCollection(Class<?> collectionClass, Collection<Object> values) {
        if (Set.class.isAssignableFrom(collectionClass)) {
            return Set.copyOf(values);
        }
        if (Queue.class.isAssignableFrom(collectionClass)) {
            return new LinkedList<>(values);
        }
        return List.copyOf(values);
    }

}
