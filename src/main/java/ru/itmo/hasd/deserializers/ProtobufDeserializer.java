package ru.itmo.hasd.deserializers;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ProtobufDeserializer<T> implements Deserializer<T> {

    @Override
    public T deserialize(Class<T> clazz, File file) throws IOException {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        try (var in = new ObjectInputStream(FileUtils.openInputStream(file))) {
            T value = schema.newMessage();
            ProtostuffIOUtil.mergeDelimitedFrom((InputStream) in, value, schema);
            return value;
        }
    }
}
