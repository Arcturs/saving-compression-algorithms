package ru.itmo.hasd.lab1.deserializers;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.hasd.lab1.model.Person;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.itmo.hasd.util.CommonUtils.FILE_NAME;

@Disabled
@ExtendWith(MockitoExtension.class)
class CustomDeserializerTest {

    private final CustomDeserializer<Person> deserializer = new CustomDeserializer<>();

    @TempDir
    private File temporaryFile;

    @Test
    @SneakyThrows
    void deserializeWithPrimitiveTypesSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        FileUtils.writeStringToFile(
                testFile,
                """
                        class Person;
                        field id, type LONG, value 10001;
                        field age, type INT, value 10;
                        field salary, type DOUBLE, value 87000.5;
                        field cash, type FLOAT, value 678097.06;
                        field name, type STRING, value QW5uYQ==;
                        field gender, type CHAR, value f;
                        field isEmployed, type BOOL, value false;
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
        assertFalse(result.isEmployed());
    }

    @Test
    @SneakyThrows
    void deserializeWithCollectionTypesSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        FileUtils.writeStringToFile(
                testFile,
                """
                        class Person;
                        field id, type LONG, value 10002;
                        field cash, type FLOAT, value 0.0;
                        field isEmployed, type BOOL, value false;
                        field hobbies, type LIST, value-type STRING, values [c3BvcnQ= || ZW5nbGlzaA==];
                        field childrenIds, type LIST, value-type INT, values [1 || 2];
                        """,
                StandardCharsets.UTF_8);

        Person result = deserializer.deserialize(Person.class, testFile);

        assertNotNull(result);
        assertEquals(10002L, result.getId());
        assertFalse(result.isEmployed());
        assertEquals(List.of("sport", "english"), result.getHobbies());
        assertEquals(Set.of(1, 2), result.getChildrenIds());
    }

    @Test
    @SneakyThrows
    void deserializeWithMapTypeSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        FileUtils.writeStringToFile(
                testFile,
                """
                       class Person;
                       field id, type LONG, value 10003;
                       field cash, type FLOAT, value 0.0;
                       field isEmployed, type BOOL, value false;
                       field luggage, type MAP, key-type STRING, value-type LONG, values [dC1zaGlydA==--3 || amVhbnM=--2];
                       """,
                StandardCharsets.UTF_8);

        Person result = deserializer.deserialize(Person.class, testFile);

        assertNotNull(result);
        assertEquals(10003L, result.getId());
        assertFalse(result.isEmployed());
        assertEquals(
                Map.of(
                        "t-shirt", 3L,
                        "jeans", 2L),
                result.getLuggage());
    }

}