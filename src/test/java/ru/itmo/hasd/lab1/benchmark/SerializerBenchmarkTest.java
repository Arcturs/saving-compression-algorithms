package ru.itmo.hasd.lab1.benchmark;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.itmo.hasd.lab1.model.HeavyObject;
import ru.itmo.hasd.lab1.model.HeavyObject2;
import ru.itmo.hasd.lab1.model.HeavyObject3;
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
import java.io.IOException;

import static ru.itmo.hasd.lab1.util.CommonUtils.FILE_NAME_TEMPLATE;
import static ru.itmo.hasd.lab1.util.CommonUtils.HEAVY_EASY_RANDOM;
import static ru.itmo.hasd.lab1.util.CommonUtils.LIGHT_EASY_RANDOM;
import static ru.itmo.hasd.lab1.util.CommonUtils.MEDIUM_EASY_RANDOM;
import static ru.itmo.hasd.lab1.util.CommonUtils.fromBytesToKb;
import static ru.itmo.hasd.lab1.util.CommonUtils.fromBytesToMb;

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

    @Test
    @SneakyThrows
    public void heavyObjectTest_1() {
        System.out.println("--- Файл с 250 строками ---");
        var avroSerializer = new AvroSerializer<HeavyObject>();
        var customSerializer = new CustomSerializer<HeavyObject>();
        var protobufSerializer = new ProtobufSerializer<HeavyObject>();
        var heavyObject = HEAVY_EASY_RANDOM.nextObject(HeavyObject.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(HeavyObject.class, heavyObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Мб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(HeavyObject.class, heavyObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Мб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(HeavyObject.class, heavyObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Мб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testProtobufFile.length())));
    }

    @Test
//    @SneakyThrows
    public void heavyObjectTest_2() throws IOException {
        System.out.println("--- Файл с 500 строками ---");
        var avroSerializer = new AvroSerializer<HeavyObject2>();
        var customSerializer = new CustomSerializer<HeavyObject2>();
        var protobufSerializer = new ProtobufSerializer<HeavyObject2>();
        var heavyObject = HEAVY_EASY_RANDOM.nextObject(HeavyObject2.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(HeavyObject2.class, heavyObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Мб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(HeavyObject2.class, heavyObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Мб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(HeavyObject2.class, heavyObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Мб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testProtobufFile.length())));
    }

    @Test
//    @SneakyThrows
    public void heavyObjectTest_3() throws IOException {
        System.out.println("--- Файл с 1000 строками ---");
        var avroSerializer = new AvroSerializer<HeavyObject3>();
        var customSerializer = new CustomSerializer<HeavyObject3>();
        var protobufSerializer = new ProtobufSerializer<HeavyObject3>();
        var heavyObject = HEAVY_EASY_RANDOM.nextObject(HeavyObject3.class);
        var testAvroFile = new File(temporaryFileAvro, FILE_NAME_TEMPLATE.formatted("avro"));
        var testCustomFile = new File(temporaryFileCustom, FILE_NAME_TEMPLATE.formatted("custom"));
        var testProtobufFile = new File(temporaryFileProtobuf, FILE_NAME_TEMPLATE.formatted("protobuf"));

        var startTimeMillis = System.currentTimeMillis();
        avroSerializer.serialize(HeavyObject3.class, heavyObject, testAvroFile);
        var endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [AVRO] Время - %s мс, Память - %s Мб [AVRO] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testAvroFile.length())));

        startTimeMillis = System.currentTimeMillis();
        customSerializer.serialize(HeavyObject3.class, heavyObject, testCustomFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [CUSTOM] Время - %s мс, Память - %s Мб [CUSTOM] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testCustomFile.length())));

        startTimeMillis = System.currentTimeMillis();
        protobufSerializer.serialize(HeavyObject3.class, heavyObject, testProtobufFile);
        endTimeMillis = System.currentTimeMillis();
        System.out.println(
                "--- [PROTOBUF] Время - %s мс, Память - %s Мб [PROTOBUF] ---"
                        .formatted(endTimeMillis - startTimeMillis, fromBytesToMb(testProtobufFile.length())));
    }

}
