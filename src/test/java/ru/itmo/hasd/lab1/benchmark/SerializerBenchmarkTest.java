package ru.itmo.hasd.lab1.benchmark;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.itmo.hasd.lab1.model.LightObject;
import ru.itmo.hasd.lab1.model.LightObject2;
import ru.itmo.hasd.lab1.model.LightObject3;
import ru.itmo.hasd.lab1.model.MediumObject;
import ru.itmo.hasd.lab1.model.MediumObject2;
import ru.itmo.hasd.lab1.model.MediumObject3;
import ru.itmo.hasd.lab1.serializers.AvroSerializer;
import ru.itmo.hasd.lab1.serializers.CustomSerializer;
import ru.itmo.hasd.lab1.serializers.ProtobufSerializer;

import java.io.File;

import static ru.itmo.hasd.util.CommonUtils.FILE_NAME_TEMPLATE;
import static ru.itmo.hasd.util.CommonUtils.LIGHT_EASY_RANDOM;
import static ru.itmo.hasd.util.CommonUtils.MEDIUM_EASY_RANDOM;
import static ru.itmo.hasd.util.CommonUtils.fromBytesToKb;

@Disabled
public class SerializerBenchmarkTest {

    @TempDir
    private File temporaryFileAvro;

    @TempDir
    private File temporaryFileCustom;

    @TempDir
    private File temporaryFileProtobuf;

    @BeforeAll
    static void beforeAll() {
        System.out.println(
                """
                * * * * * * * * * * * * * * *
                * Тестирование сериализации *
                * * * * * * * * * * * * * * *
                """);
    }

    @AfterEach
    void tearDown() {
        System.out.println();
    }

    @Test
    @SneakyThrows
    public void lightObjectTest_1() {
        System.out.println("--- Файл с 10 строками ---");
        var avroSerializer = new AvroSerializer<LightObject>();
        var customSerializer = new CustomSerializer<LightObject>();
        var protobufSerializer = new ProtobufSerializer<LightObject>();
        var lightObject = LIGHT_EASY_RANDOM.nextObject(LightObject.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(LightObject.class, lightObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(LightObject.class, lightObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(LightObject.class, lightObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

    @Test
    @SneakyThrows
    public void lightObjectTest_2() {
        System.out.println("--- Файл с 20 строками ---");
        var avroSerializer = new AvroSerializer<LightObject2>();
        var customSerializer = new CustomSerializer<LightObject2>();
        var protobufSerializer = new ProtobufSerializer<LightObject2>();
        var lightObject = LIGHT_EASY_RANDOM.nextObject(LightObject2.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(LightObject2.class, lightObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(LightObject2.class, lightObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(LightObject2.class, lightObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

    @Test
    @SneakyThrows
    public void lightObjectTest_3() {
        System.out.println("--- Файл с 40 строками ---");
        var avroSerializer = new AvroSerializer<LightObject3>();
        var customSerializer = new CustomSerializer<LightObject3>();
        var protobufSerializer = new ProtobufSerializer<LightObject3>();
        var lightObject = LIGHT_EASY_RANDOM.nextObject(LightObject3.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(LightObject3.class, lightObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(LightObject3.class, lightObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(LightObject3.class, lightObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

    @Test
    @SneakyThrows
    public void mediumObjectTest_1() {
        System.out.println("--- Файл с 50 строками ---");
        var avroSerializer = new AvroSerializer<MediumObject>();
        var customSerializer = new CustomSerializer<MediumObject>();
        var protobufSerializer = new ProtobufSerializer<MediumObject>();
        var mediumObject = MEDIUM_EASY_RANDOM.nextObject(MediumObject.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(MediumObject.class, mediumObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(MediumObject.class, mediumObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(MediumObject.class, mediumObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

    @Test
    @SneakyThrows
    public void mediumObjectTest_2() {
        System.out.println("--- Файл с 100 строками ---");
        var avroSerializer = new AvroSerializer<MediumObject2>();
        var customSerializer = new CustomSerializer<MediumObject2>();
        var protobufSerializer = new ProtobufSerializer<MediumObject2>();
        var mediumObject = MEDIUM_EASY_RANDOM.nextObject(MediumObject2.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(MediumObject2.class, mediumObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(MediumObject2.class, mediumObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(MediumObject2.class, mediumObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

    @Test
    @SneakyThrows
    public void mediumObjectTest_3() {
        System.out.println("--- Файл с 200 строками ---");
        var avroSerializer = new AvroSerializer<MediumObject3>();
        var customSerializer = new CustomSerializer<MediumObject3>();
        var protobufSerializer = new ProtobufSerializer<MediumObject3>();
        var mediumObject = MEDIUM_EASY_RANDOM.nextObject(MediumObject3.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(MediumObject3.class, mediumObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Кб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(MediumObject3.class, mediumObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Кб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(MediumObject3.class, mediumObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Кб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToKb(testProtobufFile.length())));
    }

}
