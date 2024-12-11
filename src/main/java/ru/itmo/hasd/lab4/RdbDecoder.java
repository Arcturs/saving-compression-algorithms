package ru.itmo.hasd.lab4;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.READ;
import static org.apache.hadoop.shaded.org.apache.http.Consts.ASCII;

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
            case 0xfd -> readKeyExpireSeconds(rdbReader, result);
            case 0xfc -> readKeyExpireMs(rdbReader, result);
            case 0xfb -> readResizeDb(rdbReader, result);
            case 0xfa -> readMetadataParts(rdbReader, result);
            default -> readKeyAndValue(rdbReader, result, byteNumber);
        }
    }

    private void readEof(RdbReader rdbReader, StringBuilder result) throws IOException {
        rdbReader.readChecksum();
        result.append("Конец файла \n");
        hasNext = false;
    }

    private void readDatabaseNumber(RdbReader rdbReader, StringBuilder result) throws IOException {
        var id = rdbReader.getEncodedLength();
        result.append("ИД Базы данных: %s \n".formatted(id));
    }

    private void readKeyExpireSeconds(RdbReader rdbReader, StringBuilder result) throws IOException {
        var expireTime = rdbReader.readExpireTimeInSeconds();
        result.append("Время хранения ключа в БД: %s \n".formatted(Arrays.toString(expireTime)));
    }

    private void readKeyExpireMs(RdbReader rdbReader, StringBuilder result) throws IOException {
        var expireTime = rdbReader.readExpireTimeInMs();
        result.append("Время хранения ключа в БД: %s \n".formatted(Arrays.toString(expireTime)));
    }

    private void readResizeDb(RdbReader rdbReader, StringBuilder result) throws IOException {
        var dbHashTableSize = rdbReader.getEncodedLength();
        var expiryHashTableSize = rdbReader.getEncodedLength();
        result.append(
                "Размер хэш таблицы БД: %s, Размер expire хэш таблицы: %s \n"
                        .formatted(dbHashTableSize, expiryHashTableSize));
    }

    private void readMetadataParts(RdbReader rdbReader, StringBuilder result) throws IOException {
        var key = rdbReader.readEncodedValue();
        var value = rdbReader.readEncodedValue();
        result.append("Метаданные %s: %s \n".formatted(new String(key, ASCII), new String(value, ASCII)));
    }

    private void readKeyAndValue(RdbReader rdbReader, StringBuilder result, int byteNumber) throws IOException {
        var key = rdbReader.readEncodedValue();
        String value = switch (byteNumber) {
            case 0 -> new String(rdbReader.readEncodedValue(), ASCII);
            case 1, 2 -> {
                int length = rdbReader.getEncodedLength();
                var list = new ArrayList<String>();
                for (int i = 0; i < length; i++) {
                    list.add(new String(rdbReader.readEncodedValue(), ASCII));
                }
                yield "[ " + StringUtils.join(list, ", ") + " ]";
            }
            case 4 -> {
                int length = rdbReader.getEncodedLength();
                var hash = new ArrayList<String>();
                for (int i = 0; i < length; i++) {
                    hash.add(
                            new String(rdbReader.readEncodedValue(), ASCII)
                                    + ": "
                                    + new String(rdbReader.readEncodedValue(), ASCII));
                }
                yield "{ " + StringUtils.join(hash, ", ") + " }";
            }
            default -> throw new IllegalStateException("Неподдерживаемое значение " + byteNumber);
        };
        result.append("Ключ: %s, Значение: %s \n".formatted(new String(key, ASCII), value));
    }

}
