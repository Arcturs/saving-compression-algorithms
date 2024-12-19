package ru.itmo.hasd.lab4;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class RdbDecoderTest {

    private final RdbDecoder rdbDecoder = new RdbDecoder();

    @Test
    void decodeFileTest() {
        var result = rdbDecoder.decodeFile("/tmp/redisdb_data/dump.rdb");

        assertNotNull(result);
        System.out.println(result);
    }

}