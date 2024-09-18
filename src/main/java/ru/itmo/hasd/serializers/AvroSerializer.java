package ru.itmo.hasd.serializers;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;

public class AvroSerializer<T> implements Serializer<T> {

    @Override
    public void serialize(Class<T> clazz, T value, File file) throws IOException {
        var schema = ReflectData.get().getSchema(clazz);
        DatumWriter<T> writer = new ReflectDatumWriter<T>(schema);

        try (DataFileWriter<T> out = new DataFileWriter<T>(writer)) {
            out.setCodec(CodecFactory.deflateCodec(9))
                    .create(schema, file);
            out.append(value);
        }
    }

}
