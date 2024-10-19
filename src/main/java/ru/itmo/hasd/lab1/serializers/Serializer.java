package ru.itmo.hasd.lab1.serializers;

import java.io.File;
import java.io.IOException;

public interface Serializer<T> {

    void serialize(Class<T> clazz, T value, File file) throws IOException;

}
