package ru.itmo.hasd.lab2;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.hasd.lab2.processor.BookDataProcessor;
import ru.itmo.hasd.lab2.processor.BookRatingProcessor;
import ru.itmo.hasd.lab2.reader.FileReader;
import ru.itmo.hasd.lab2.reader.OrcFileReader;
import ru.itmo.hasd.lab2.reader.ParquetFileReader;
import ru.itmo.hasd.lab2.writer.FileWriter;
import ru.itmo.hasd.lab2.writer.OrcFileWriter;
import ru.itmo.hasd.lab2.writer.ParquetFileWriter;

import java.io.File;

import static ru.itmo.hasd.lab2.constants.Constants.BOOKS_DATA_FILE_PATH_FOR_TEST;
import static ru.itmo.hasd.lab2.constants.Constants.BOOKS_RATING_FILE_PATH_FOR_TEST;

@Disabled
@ExtendWith(MockitoExtension.class)
public class SparkTest {

    private static final String PARQUET_FILE_PATH = "/tmp/spark/parquet";
    private static final String ORC_FILE_PATH = "/tmp/spark/orc";

    private static final String SNAPPY = "snappy";
    private static final String ZSTD = "zstd";

    private final FileWriter orcFileWriter = new OrcFileWriter();
    private final FileWriter parquetFileWriter = new ParquetFileWriter();
    private final FileReader orcFileReader = new OrcFileReader();
    private final FileReader parquetFileReader = new ParquetFileReader();

    @Test
    void bookDataSnappyTest() {
        var originalFile = new File(BOOKS_DATA_FILE_PATH_FOR_TEST);
        var bookDataProcessor = new BookDataProcessor();
        System.out.println("[D, Snappy] Оригинальный файл: " + fromBytesToMb(originalFile.length()) + " Мб");
        var processedDataset = bookDataProcessor.process();

        var now = System.currentTimeMillis();
        parquetFileWriter.write(processedDataset, PARQUET_FILE_PATH, SNAPPY);
        var parquetFileWriterTime = System.currentTimeMillis() - now;
        var parquetFile = new File(PARQUET_FILE_PATH);
        System.out.println("Время записи в parquet файл: " + fromMsToSeconds(parquetFileWriterTime) + " с");
        System.out.println("Занимаемая память parquet файла: " + fromBytesToMb(FileUtils.sizeOfDirectory(parquetFile)) + " Мб");
        now = System.currentTimeMillis();
        orcFileWriter.write(processedDataset, ORC_FILE_PATH, SNAPPY);
        var orcFileWriterTime = System.currentTimeMillis() - now;
        var orcFile = new File(ORC_FILE_PATH);
        System.out.println("Время записи в orc файл: " + fromMsToSeconds(orcFileWriterTime) + " с");
        System.out.println("Занимаемая память orc файла: " + fromBytesToMb(FileUtils.sizeOfDirectory(orcFile)) + " Мб");

        now = System.currentTimeMillis();
        parquetFileReader.read(PARQUET_FILE_PATH);
        var parquetFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из parquet файл: " + parquetFileReaderTime + " мс");
        now = System.currentTimeMillis();
        orcFileReader.read(ORC_FILE_PATH);
        var orcFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из orc файл: " + orcFileReaderTime + " мс");
    }

    @Test
    void bookDataZstdTest() {
        var originalFile = new File(BOOKS_DATA_FILE_PATH_FOR_TEST);
        var bookDataProcessor = new BookDataProcessor();
        System.out.println("[D, Zstd] Оригинальный файл: " + fromBytesToMb(originalFile.length()) + " Мб");
        var processedDataset = bookDataProcessor.process();

        var now = System.currentTimeMillis();
        parquetFileWriter.write(processedDataset, PARQUET_FILE_PATH, ZSTD);
        var parquetFileWriterTime = System.currentTimeMillis() - now;
        var parquetFile = new File(PARQUET_FILE_PATH);
        System.out.println("Время записи в parquet файл: " + fromMsToSeconds(parquetFileWriterTime) + " с");
        System.out.println("Занимаемая память parquet файла: " + fromBytesToMb(FileUtils.sizeOfDirectory(parquetFile)) + " Мб");
        now = System.currentTimeMillis();
        orcFileWriter.write(processedDataset, ORC_FILE_PATH, ZSTD);
        var orcFileWriterTime = System.currentTimeMillis() - now;
        var orcFile = new File(ORC_FILE_PATH);
        System.out.println("Время записи в orc файл: " + fromMsToSeconds(orcFileWriterTime) + " с");
        System.out.println("Занимаемая память orc файла: " + fromBytesToMb(FileUtils.sizeOfDirectory(orcFile)) + " Мб");

        now = System.currentTimeMillis();
        parquetFileReader.read(PARQUET_FILE_PATH);
        var parquetFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из parquet файл: " + parquetFileReaderTime + " мс");
        now = System.currentTimeMillis();
        orcFileReader.read(ORC_FILE_PATH);
        var orcFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из orc файл: " + orcFileReaderTime + " мс");
    }

    @Test
    void bookRatingSnappyTest() {
        var originalFile = new File(BOOKS_RATING_FILE_PATH_FOR_TEST);
        var bookRatingProcessor = new BookRatingProcessor();
        System.out.println("[R, Snappy] Оригинальный файл: " + fromBytesToGb(originalFile.length()) + " Гб");
        var processedDataset = bookRatingProcessor.process();

        var now = System.currentTimeMillis();
        parquetFileWriter.write(processedDataset, PARQUET_FILE_PATH, SNAPPY);
        var parquetFileWriterTime = System.currentTimeMillis() - now;
        var parquetFile = new File(PARQUET_FILE_PATH);
        System.out.println("Время записи в parquet файл: " + fromMsToSeconds(parquetFileWriterTime) + " с");
        System.out.println("Занимаемая память parquet файла: " + fromBytesToGb(FileUtils.sizeOfDirectory(parquetFile)) + " Гб");
        now = System.currentTimeMillis();
        orcFileWriter.write(processedDataset, ORC_FILE_PATH, SNAPPY);
        var orcFileWriterTime = System.currentTimeMillis() - now;
        var orcFile = new File(ORC_FILE_PATH);
        System.out.println("Время записи в orc файл: " + fromMsToSeconds(orcFileWriterTime) + " с");
        System.out.println("Занимаемая память orc файла: " + fromBytesToGb(FileUtils.sizeOfDirectory(orcFile)) + " Гб");

        now = System.currentTimeMillis();
        parquetFileReader.read(PARQUET_FILE_PATH);
        var parquetFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из parquet файл: " + parquetFileReaderTime + " мс");
        now = System.currentTimeMillis();
        orcFileReader.read(ORC_FILE_PATH);
        var orcFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из orc файл: " + orcFileReaderTime + " мс");
    }

    @Test
    void bookRatingZstdTest() {
        var originalFile = new File(BOOKS_RATING_FILE_PATH_FOR_TEST);
        var bookRatingProcessor = new BookRatingProcessor();
        System.out.println("[R, Zstd] Оригинальный файл: " + fromBytesToGb(originalFile.length()) + " Гб");
        var processedDataset = bookRatingProcessor.process();

        var now = System.currentTimeMillis();
        parquetFileWriter.write(processedDataset, PARQUET_FILE_PATH, ZSTD);
        var parquetFileWriterTime = System.currentTimeMillis() - now;
        var parquetFile = new File(PARQUET_FILE_PATH);
        System.out.println("Время записи в parquet файл: " + fromMsToSeconds(parquetFileWriterTime) + " с");
        System.out.println("Занимаемая память parquet файла: " + fromBytesToGb(FileUtils.sizeOfDirectory(parquetFile)) + " Гб");
        now = System.currentTimeMillis();
        orcFileWriter.write(processedDataset, ORC_FILE_PATH, ZSTD);
        var orcFileWriterTime = System.currentTimeMillis() - now;
        var orcFile = new File(ORC_FILE_PATH);
        System.out.println("Время записи в orc файл: " + fromMsToSeconds(orcFileWriterTime) + " с");
        System.out.println("Занимаемая память orc файла: " + fromBytesToGb(FileUtils.sizeOfDirectory(orcFile)) + " Гб");

        now = System.currentTimeMillis();
        parquetFileReader.read(PARQUET_FILE_PATH);
        var parquetFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из parquet файл: " + parquetFileReaderTime + " мс");
        now = System.currentTimeMillis();
        orcFileReader.read(ORC_FILE_PATH);
        var orcFileReaderTime = System.currentTimeMillis() - now;
        System.out.println("Время чтения из orc файл: " + orcFileReaderTime + " мс");
    }

    private static long fromBytesToMb(long bytes) {
        return bytes / 1024 / 1024;
    }

    private static double fromBytesToGb(long bytes) {
        return (double) bytes / 1024 / 1024 / 1024;
    }

    private static double fromMsToSeconds(long ms) {
        return (double) ms / 1000;
    }

}
