package ru.itmo.hasd.serializers;

import ru.itmo.hasd.schema.Field;
import ru.itmo.hasd.schema.FieldType;
import ru.itmo.hasd.schema.Schema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.itmo.hasd.constant.CommonConstants.ExceptionMessages.FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE;
import static ru.itmo.hasd.constant.CommonConstants.ExceptionMessages.SERIALIZATION_EXCEPTION_MESSAGE;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.EMPTY_VALUES;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.ENTRY_DELIMITER;
import static ru.itmo.hasd.constant.CommonConstants.SchemaParts.VALUES_DELIMITER;
import static ru.itmo.hasd.schema.FieldType.LIST;
import static ru.itmo.hasd.schema.FieldType.MAP;

public class CustomSerializer<T> implements Serializer<T> {

    @Override
    public void serialize(Class<T> clazz, T value, File file) throws IOException {
        var schema = getSchema(clazz, value);

        try (var out = new FileOutputStream(file)) {
            out.write(schema.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    private Schema getSchema(Class<T> clazz, T value) {
        return new Schema(
                clazz.getSimpleName(),
                Arrays.stream(clazz.getDeclaredFields())
                        .map(field -> {
                            field.setAccessible(true);
                            return getField(field, value);
                        })
                        .filter(Objects::nonNull)
                        .toList());
    }

    private Field getField(java.lang.reflect.Field field, T value) {
        var fieldType = FieldType.fromClassType(field.getType());

        if (fieldType == LIST) {
            var fieldValues = getFieldValues(field, value);
            if (fieldValues == null) {
                return null;
            }
            var fieldValuesType = (ParameterizedType) field.getGenericType();
            return new Field()
                    .setName(field.getName())
                    .setType(fieldType)
                    .setValueType(FieldType.fromClassType((Class<?>) fieldValuesType.getActualTypeArguments()[0]))
                    .setValue(fieldValues);
        }

        if (fieldType == MAP) {
            var fieldValues = getFieldMapValues(field, value);
            if (fieldValues == null) {
                return null;
            }
            var fieldValuesType = (ParameterizedType) field.getGenericType();
            return new Field()
                    .setName(field.getName())
                    .setType(fieldType)
                    .setKeyType(FieldType.fromClassType((Class<?>) fieldValuesType.getActualTypeArguments()[0]))
                    .setValueType(FieldType.fromClassType((Class<?>) fieldValuesType.getActualTypeArguments()[1]))
                    .setValue(fieldValues);
        }

        var fieldValue = getFieldValue(field, value);
        if (fieldValue == null) {
            return null;
        }
        return new Field()
                .setName(field.getName())
                .setType(fieldType)
                .setValueType(null)
                .setValue(fieldValue);
    }

    private String getFieldValue(java.lang.reflect.Field field, T value) {
        try {
            var fieldValue = field.get(value);
            if (fieldValue == null) {
                return null;
            }
            return fieldValue.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (Exception e) {
            throw new RuntimeException(SERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

    private String getFieldValues(java.lang.reflect.Field field, T value) {
        try {
            var collection = (Collection<Object>) field.get(value);
            if (collection == null) {
                return null;
            }
            if (collection.isEmpty()) {
                return EMPTY_VALUES;
            }
            return collection.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(VALUES_DELIMITER, "[", "]"));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (Exception e) {
            throw new RuntimeException(SERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

    private String getFieldMapValues(java.lang.reflect.Field field, T value) {
        try {
            var map = (Map<Object, Object>) field.get(value);
            if (map == null) {
                return null;
            }
            if (map.isEmpty()) {
                return EMPTY_VALUES;
            }
            return map.entrySet()
                    .stream()
                    .map(entry -> entry.getKey().toString() + ENTRY_DELIMITER + entry.getValue().toString())
                    .collect(Collectors.joining(VALUES_DELIMITER, "[", "]"));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE.formatted(field.getName()));
        } catch (Exception e) {
            throw new RuntimeException(SERIALIZATION_EXCEPTION_MESSAGE, e);
        }
    }

}
