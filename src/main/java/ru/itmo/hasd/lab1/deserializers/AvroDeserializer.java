package ru.itmo.hasd.lab1.deserializers;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectDatumReader;

import java.io.File;
import java.io.IOException;

public class AvroDeserializer<T> implements Deserializer<T> {

    @Override
    public T deserialize(Class<T> clazz, File file) throws IOException {
        DatumReader<T> reader = new ReflectDatumReader<>(clazz);

        try (DataFileReader<T> in = new DataFileReader<>(file, reader)) {
            return in.next();
        }
    }

}
