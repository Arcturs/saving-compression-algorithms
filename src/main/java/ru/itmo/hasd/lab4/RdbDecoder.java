package ru.itmo.hasd.lab4;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class RdbDecoder {

    private static final String REDIS = "REDIS";

    private final ByteBuffer buffer = ByteBuffer.allocateDirect(8 * 1024);

    private boolean hasNext = true;

    public String decodeFile(String path) {
        var result = new StringBuilder();

        try (var channel = FileChannel.open(Path.of(path), READ)) {
            var rdbReader = new RdbReader(buffer, channel);
            rdbReader.fillBuffer();
            getMetadata(rdbReader, result);

            while (hasNext) {
                var specialByte = rdbReader.readSpecialByte();
                readFilePart(rdbReader, specialByte, result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return result.toString();
    }

    private void getMetadata(RdbReader rdbReader, StringBuilder result) throws IOException {
        var magicNumber = rdbReader.readMagicNumber();
        if (!magicNumber.equals(REDIS)) {
            throw new IllegalArgumentException("Не валидный файл rdb");
        }

        var version = rdbReader.readVersion();
        result.append("Файл Redis версии %d \n".formatted(version));
    }

    private void readFilePart(RdbReader rdbReader, int byteNumber, StringBuilder result) throws IOException {
        switch (byteNumber) {
            case 0xff -> readEof(rdbReader, result);
            case 0xfe -> readDatabaseNumber(rdbReader, result);
            case 0xfd -> null;
            case 0xfc -> null;
            case 0xfb -> null;
            case 0xfa -> null;
            default -> null;
        };
    }

    private void readEof(RdbReader rdbReader, StringBuilder result) throws IOException {
        rdbReader.readChecksum();
        result.append("Конец файла \n");
        hasNext = false;
    }

    private void readDatabaseNumber(RdbReader rdbReader, StringBuilder result) throws IOException {

    }

}
