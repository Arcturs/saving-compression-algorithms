package ru.itmo.hasd.deserializers;

import java.io.File;
import java.io.IOException;

public interface Deserializer<T> {

    T deserialize(Class<T> clazz, File file) throws IOException;
}
