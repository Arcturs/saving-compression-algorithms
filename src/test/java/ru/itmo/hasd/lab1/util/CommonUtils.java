package ru.itmo.hasd.lab1.util;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

@UtilityClass
public class CommonUtils {

    public static final String FILE_NAME_TEMPLATE = "test_%s.bin";
    public static final String FILE_NAME = "test.bin";

    public static final EasyRandom LIGHT_EASY_RANDOM = new EasyRandom(
            new EasyRandomParameters()
                    .stringLengthRange(1, 100)
                    .collectionSizeRange(1, 50));
    public static final EasyRandom MEDIUM_EASY_RANDOM = new EasyRandom(
            new EasyRandomParameters()
                    .stringLengthRange(100, 1000)
                    .collectionSizeRange(25, 50));
    public static final EasyRandom HEAVY_EASY_RANDOM = new EasyRandom(
            new EasyRandomParameters()
                    .stringLengthRange(1000, 5000)
                    .collectionSizeRange(50, 100));

    public static Long fromBytesToKb(Long length) {
        return length / 1024;
    }

    public static Long fromBytesToMb(Long length) {
        return length / 1024 / 1024;
    }

}
