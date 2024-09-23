package ru.itmo.hasd.deserializers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomDeserializer<T> implements Deserializer<T> {

    @Override
    public T deserialize(Class<T> clazz, File file) throws IOException {
        try (var in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)))) {

            var line = in.readLine();
            while (line != null) {
                // TODO: finish
                line = in.readLine();
            }
        }
    }

    private void processLine()

}
