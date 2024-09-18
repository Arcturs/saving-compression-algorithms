package ru.itmo.hasd.deserializers;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectDatumReader;

import java.io.File;
import java.io.IOException;

public class AvroDeserializer<T> {

    public T deserialize(Class<T> clazz, File file) throws IOException {
        DatumReader<T> reader = new ReflectDatumReader<T>(clazz);

        try (DataFileReader<T> in = new DataFileReader<T>(file, reader)) {
            return in.next();
        }
    }

}
