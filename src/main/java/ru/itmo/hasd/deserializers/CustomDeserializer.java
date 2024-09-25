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

import static ru.itmo.hasd.constant.CommonConstants.ExceptionMessages.DESERIALIZATION_EXCEPTION_MESSAGE;
import static ru.itmo.hasd.constant.CommonConstants.ExceptionMessages.FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.CLASS;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.ENTRY_DELIMITER;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.FIELD;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.KEY_TYPE;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.TYPE;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.VALUE;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.VALUES;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.VALUES_DELIMITER_REGEX;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.VALUE_TYPE;
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
            if (line.startsWith(CLASS)) {
                return clazz.getDeclaredConstructor().newInstance();
            }

            var tokens = line.split("[,;]");
            var field = clazz.getDeclaredField(tokens[0].trim().substring(FIELD.length()));
            field.setAccessible(true);
            var fieldType = FieldType.fromName(tokens[1].trim().substring(TYPE.length()));

            if (line.contains(KEY_TYPE)) {
                if (fieldType != MAP) {
                    throw new IllegalArgumentException("Указан неверный тип поля для словаря для десериализации");
                }
                setFieldMapValues(
                        field, result,
                        FieldType.fromName(tokens[2].trim().substring(KEY_TYPE.length())),
                        FieldType.fromName(tokens[3].trim().substring(VALUE_TYPE.length())),
                        tokens[4].trim().substring(VALUES.length(), tokens[4].length() - 2));
                return result;
            }

            if (line.contains(VALUE_TYPE)) {
                if (fieldType != LIST) {
                    throw new IllegalArgumentException("Указан неверный тип поля для коллекции для десериализации");
                }
                setFieldValues(
                        field, result,
                        FieldType.fromName(tokens[2].trim().substring(VALUE_TYPE.length())),
                        tokens[3].trim().substring(VALUES.length(), tokens[3].length() - 2));
                return result;
            }

            if (line.startsWith(FIELD)) {
                setFieldValue(
                        field, result, fieldType,
                        tokens[2].trim().substring(VALUE.length()));
                return result;
            }

            throw new IllegalArgumentException("Неизвестный компонент схемы: " + line);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(
                    "Произошла ошибка при десериализации поля %s связанная с отсутствием поля в искомом классе %s"
                            .formatted(line, clazz.getSimpleName()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(line));
        } catch (Exception e) {
            throw new RuntimeException(DESERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

    private void setFieldValue(Field field, T object, FieldType type, String value) {
        try {
            var parsedValue = parseValueFromString(type, value);
            field.set(object, parsedValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к типу %s"
                            .formatted(field.getName(), type.name()));
        } catch (Exception e) {
            throw new RuntimeException(DESERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

    private void setFieldValues(Field field, T object, FieldType valueFieldType, String value) {
        try {
            var elements = value.split(VALUES_DELIMITER_REGEX);
            Collection<Object> deserializedElements = new ArrayList<>(elements.length);
            for (var element : elements) {
                deserializedElements.add(parseValueFromString(valueFieldType, element));
            }
            field.set(object, getCollection(field.getType(), deserializedElements));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к коллекции типа %s"
                            .formatted(field.getName(), valueFieldType.name()));
        } catch (Exception e) {
            throw new RuntimeException(DESERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

    private void setFieldMapValues(
            Field field, T object,
            FieldType keyFieldType,
            FieldType valueFieldType,
            String value) {

        try {
            var elements = value.split(VALUES_DELIMITER_REGEX);
            Map<Object, Object> deserializedMap = new HashMap<>(elements.length);
            for (var element : elements) {
                var entry = element.split(ENTRY_DELIMITER);
                deserializedMap.put(
                        parseValueFromString(keyFieldType, entry[0]),
                        parseValueFromString(valueFieldType, entry[1]));
            }
            field.set(object, deserializedMap);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Произошла ошибка при попытке привести поле %s к словарю типа %s"
                            .formatted(field.getName(), valueFieldType.name()));
        } catch (Exception e) {
            throw new RuntimeException(DESERIALIZATION_EXCEPTION_MESSAGE, e);
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
