package ru.itmo.hasd.lab5;

import com.google.common.primitives.Bytes;
import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFEncoder;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;

public class Processor {

    public LoudsTree createLoudsTree(String path) {
        var words = readWords(path);
        var trie = new Trie();
        words.forEach(trie::add);
        return trie.convert();
    }

    public Scanner createFileIndex(String path, String filePath) {
        var words = readWords(path);
        var out = new File(filePath);
        try (var writer = new FileWriter(out)) {
            for (var word : words) {
                writer.write(word + "\n");
            }
            return new Scanner(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public File createLzfFile(String path, String filePath) {
        var words = readWords(path);
        var out = new File(filePath);
        List<byte[]> bytes = new ArrayList<>();
        for (var word : words) {
            var encoded = LZFEncoder.encode((word + "\n").getBytes(StandardCharsets.UTF_8));
            bytes.add(encoded);
        }
        Files.write(
                out.toPath(),
                bytes.stream()
                        .map(String::new)
                        .toList());
        return out;
    }

    private Collection<String> readWords(String path) {
        var words = new HashSet<String>();
        try (var reader = Files.newByteChannel(Path.of(path), READ)) {
            var byteBuffer = ByteBuffer.allocate(1024 * 10);
            while (reader.read(byteBuffer) > 0) {
                byteBuffer.flip();
                words.addAll(
                        Arrays.stream(new String(byteBuffer.array())
                                        .trim()
                                        .split("\\W+"))
                                .map(String::toLowerCase)
                                .toList());
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        if (words.isEmpty()) {
            throw new IllegalStateException("Слов для обработки нет");
        }
        return words;
    }

}
