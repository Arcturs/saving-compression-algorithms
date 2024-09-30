package ru.itmo.hasd.benchmark;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.itmo.hasd.model.HeavyObject;
import ru.itmo.hasd.model.LightObject;
import ru.itmo.hasd.model.MediumObject;
import ru.itmo.hasd.serializers.AvroSerializer;
import ru.itmo.hasd.serializers.CustomSerializer;
import ru.itmo.hasd.serializers.ProtobufSerializer;

import java.io.File;

import static ru.itmo.hasd.util.CommonUtils.FILE_NAME_TEMPLATE;
import static ru.itmo.hasd.util.CommonUtils.HEAVY_EASY_RANDOM;
import static ru.itmo.hasd.util.CommonUtils.LIGHT_EASY_RANDOM;
import static ru.itmo.hasd.util.CommonUtils.MEDIUM_EASY_RANDOM;
import static ru.itmo.hasd.util.CommonUtils.fromBytesToKb;
import static ru.itmo.hasd.util.CommonUtils.fromBytesToMb;

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
    public void lightObjectTest() {
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
    public void mediumObjectTest() {
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
    public void heavyObjectTest() {
        System.out.println("--- Файл с 100 строками ---");
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

}
