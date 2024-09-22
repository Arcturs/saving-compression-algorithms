package ru.itmo.hasd.serializers;

import ru.itmo.hasd.schema.Field;
import ru.itmo.hasd.schema.FieldType;
import ru.itmo.hasd.schema.Schema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CustomSerializer<T> implements Serializer<T> {

    @Override
    public void serialize(Class<T> clazz, T value, File file) throws IOException {
        var schema = getSchema(clazz, value);

        try (var out = new FileOutputStream(file)) {
            // TODO: think
            out.write(schema.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    private Schema getSchema(Class<T> clazz, T value) {
        return new Schema()
                .setName(clazz.getSimpleName())
                .setFields(
                        Arrays.stream(clazz.getFields())
                                .map(field -> {
                                    field.setAccessible(true);
                                    return new Field()
                                            .setName(field.getName())
                                            .setType(FieldType.fromField(field))
                                            .setValue(getFieldValue(field, value));
                                })
                                .toList());
    }

    private String getFieldValue(java.lang.reflect.Field field, T value) {
        try {
            return field.get(value).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}