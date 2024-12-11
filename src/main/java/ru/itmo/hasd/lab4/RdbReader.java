package ru.itmo.hasd.lab4;

import com.ning.compress.lzf.LZFDecoder;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.apache.hadoop.shaded.org.apache.http.Consts.ASCII;

@AllArgsConstructor
public class RdbReader {

    private final ByteBuffer buffer;
    private final FileChannel channel;

    public void fillBuffer() throws IOException {
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
    }

    public String readMagicNumber() throws IOException {
        return new String(readBytes(5), ASCII);
    }

    public int readVersion() throws IOException {
        return Integer.parseInt(new String(readBytes(4), ASCII));
    }

    public byte[] readChecksum() throws IOException {
        return readBytes(8);
    }

    public byte[] readEncodedValue() throws IOException {
        int firstByte = readSpecialByte();
        int flag = (firstByte & 0xc0) >> 6; // Определяем первые 2 бита для определения длины value
        int leftSixBits = firstByte & 0x3f;
        return switch (flag) {
            case 0, 1, 2 -> readBytes(getEncodedLength(flag, leftSixBits));
            case 3 -> readSpecialEncodedValue(leftSixBits); // кодировка в особенном формате, формат определяется оставшимися 6 битами
            default -> throw new IllegalStateException("Неподдерживаемое значение длины " + flag);
        };
    }

    public int readSpecialByte() throws IOException {
        return readSignedByte() & 0xff;
    }

    public byte[] readExpireTimeInSeconds() throws IOException {
        return readBytes(4);
    }

    public byte[] readExpireTimeInMs() throws IOException {
        return readBytes(8);
    }

    public int getEncodedLength() throws IOException {
        int firstByte = readSpecialByte();
        int flag = (firstByte & 0xc0) >> 6; // Определяем первые 2 бита для определения длины value
        int leftSixBits = firstByte & 0x3f;
        return getEncodedLength(flag, leftSixBits);
    }

    private int getEncodedLength(int flag, int leftSixBits) throws IOException {
        return switch (flag) {
            case 0 -> leftSixBits; // оставшиеся 6 битов
            case 1 -> (readSpecialByte() & 0xff) | (leftSixBits << 8); // 1 специальный байт + 6 оставшиеся бита
            case 2 -> {
                byte[] bs = readBytes(4); // 6 оставшихся битов пропускаются, 4 следующих байта
                yield (bs[0] & 0xff) << 24
                        | (bs[1] & 0xff) << 16
                        | (bs[2] & 0xff) <<  8
                        | (bs[3] & 0xff);
            }
            default -> throw new IllegalStateException("Неподдерживаемое значение длины" + flag);
        };
    }

    private byte[] readSpecialEncodedValue(int flag) throws IOException {
        return switch (flag) {
            case 0 -> String.valueOf(readSignedByte()).getBytes(ASCII);
            case 1 -> String.valueOf((readSpecialByte() & 0xff) | (readSignedByte() << 8)).getBytes(ASCII);
            case 2 -> {
                byte[] bs = readBytes(4);
                var len = (int) bs[3] << 24
                        | ((int) bs[2] & 0xff) << 16
                        | ((int) bs[1] & 0xff) <<  8
                        | ((int) bs[0] & 0xff);
                yield String.valueOf(len).getBytes(ASCII);
            }
            case 3 -> decodeLzfString();
            default -> String.valueOf(flag).getBytes(ASCII);
        };
    }

    private int readSignedByte() throws IOException {
        if (!buffer.hasRemaining()) {
            fillBuffer();
        }
        return buffer.get();
    }

    public byte[] readBytes(int numBytes) throws IOException {
        int remaining = numBytes;
        int position = 0;
        byte[] bytes = new byte[numBytes];

        while (remaining > 0) {
            int available = buffer.remaining();
            if (available >= remaining) {
                buffer.get(bytes, position, remaining);
                position += remaining;
                remaining = 0;
            } else {
                buffer.get(bytes, position, available);
                position += available;
                remaining -= available;
                fillBuffer();
            }
        }
        return bytes;
    }

    private byte[] decodeLzfString() throws IOException {
        int sourceLength = getEncodedLength();
        int destinationLength = getEncodedLength();
        byte[] source = readBytes(sourceLength);
        byte[] destination = new byte[destinationLength];
        LZFDecoder.decode(source, destination);
        return destination;
    }

}
