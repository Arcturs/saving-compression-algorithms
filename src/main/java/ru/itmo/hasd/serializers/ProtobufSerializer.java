package ru.itmo.hasd.serializers;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ProtobufSerializer<T> implements Serializer<T> {

    @Override
    public void serialize(Class<T> clazz, T value, File file) throws IOException {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        try (var out = new ObjectOutputStream(FileUtils.openOutputStream(file))) {
            ProtostuffIOUtil.writeDelimitedTo(out, value, schema);
            out.flush();
        }
    }

}
