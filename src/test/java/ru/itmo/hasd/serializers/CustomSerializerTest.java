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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomSerializerTest {

    private static final String FILE_NAME = "test.bin";

    private final CustomSerializer<Person> serializer = new CustomSerializer<>();

    @TempDir
    private File temporaryFile;

    @Test
    @SneakyThrows
    void serializeObjectWithPrimitiveTypesSuccessTest() {
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

    @Test
    @SneakyThrows
    void serializeObjectWithCollectionTypesSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        var person = new Person()
                .setId(10002L)
                .setHobbies(List.of("sport", "english"))
                .setChildrenIds(Set.of(1, 2));

        serializer.serialize(Person.class, person, testFile);

        String result = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        assertTrue(
                result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10002;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field hobbies, type LIST, value-type STRING, values [sport || english];
                                field childrenIds, type LIST, value-type INT, values [1 || 2];
                                """)
                        || result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10002;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field hobbies, type LIST, value-type STRING, values [english || sport];
                                field childrenIds, type LIST, value-type INT, values [1 || 2];
                                """)
                        || result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10002;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field hobbies, type LIST, value-type STRING, values [english || sport];
                                field childrenIds, type LIST, value-type INT, values [2 || 1];
                                """)
                        || result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10002;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field hobbies, type LIST, value-type STRING, values [sport || english];
                                field childrenIds, type LIST, value-type INT, values [2 || 1];
                                """));
    }

    @Test
    @SneakyThrows
    void serializeObjectWithMapTypeSuccessTest() {
        var testFile = new File(temporaryFile, FILE_NAME);
        var person = new Person()
                .setId(10003L)
                .setLuggage(
                        Map.of(
                                "t-shirt", 3L,
                                "jeans", 2L));

        serializer.serialize(Person.class, person, testFile);

        String result = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        assertTrue(
                result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10003;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field luggage, type MAP, key-type STRING, value-type LONG, values [t-shirt--3 || jeans--2];
                                """)
                        || result.contains(
                        """
                                class Person;
                                field id, type LONG, value 10003;
                                field cash, type FLOAT, value 0.0;
                                field isEmployed, type BOOL, value false;
                                field luggage, type MAP, key-type STRING, value-type LONG, values [jeans--2 || t-shirt--3];
                                """));
    }

}
