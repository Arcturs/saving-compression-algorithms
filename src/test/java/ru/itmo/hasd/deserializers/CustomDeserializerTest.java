package ru.itmo.hasd.deserializers;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.hasd.model.Person;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomDeserializerTest {

    private static final String FILE_NAME = "test.bin";

    private final CustomDeserializer<Person> deserializer = new CustomDeserializer<>();

    @TempDir
    private File temporaryFile;

    @Test
    @SneakyThrows
    void deserializeSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        FileUtils.writeStringToFile(
                testFile,
                """
                        class Person;
                        field id, type LONG, value 10001;
                        field age, type INT, value 10;
                        field salary, type DOUBLE, value 87000.5;
                        field cash, type FLOAT, value 678097.06;
                        field name, type STRING, value Anna;
                        field gender, type CHAR, value f;
                        """,
                StandardCharsets.UTF_8);

        Person result = deserializer.deserialize(Person.class, testFile);

        assertNotNull(result);
        assertEquals(10001L, result.getId());
        assertEquals(10, result.getAge());
        assertEquals(87000.5, result.getSalary());
        assertEquals(678097.06F, result.getCash());
        assertEquals("Anna", result.getName());
        assertEquals('f', result.getGender());
    }

}