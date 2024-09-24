package ru.itmo.hasd.serializers;

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

@ExtendWith(MockitoExtension.class)
public class CustomSerializerTest {

    private static final String FILE_NAME = "test.bin";

    private final CustomSerializer<Person> serializer = new CustomSerializer<>();

    @TempDir
    private File temporaryFile;

    @Test
    @SneakyThrows
    void serializeSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        var person = new Person()
                .setId(10001L)
                .setAge(10)
                .setSalary(87000.50)
                .setCash(678097.09F)
                .setName("Anna")
                .setGender('f')
                .setEmployed(false);

        serializer.serialize(Person.class, person, testFile);

        String result = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        // Note: теряется точность у float и double чисел - на подумать
        assertEquals(
                """
                class Person;
                field id, type LONG, value 10001;
                field age, type INT, value 10;
                field salary, type DOUBLE, value 87000.5;
                field cash, type FLOAT, value 678097.06;
                field name, type STRING, value Anna;
                field gender, type CHAR, value f;
                field isEmployed, type BOOL, value false;
                """,
                result
        );
    }

}
