package ru.itmo.hasd.lab2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.hasd.lab2.processor.SparkProcessor;
import ru.itmo.hasd.lab2.reader.OrcFileReader;
import ru.itmo.hasd.lab2.reader.ParquetFileReader;
import ru.itmo.hasd.lab2.writer.OrcFileWriter;
import ru.itmo.hasd.lab2.writer.ParquetFileWriter;

import java.io.File;

import static ru.itmo.hasd.util.CommonUtils.fromBytesToKb;

@ExtendWith(MockitoExtension.class)
public class SparkTest {

    private final SparkProcessor processor = new SparkProcessor();

    @Test
    void test() {
        var orcWriter = new OrcFileWriter();
        var orcReader = new OrcFileReader();
        var parquetWriter = new ParquetFileWriter();
        var parquetReader = new ParquetFileReader();

        var parquetFile = new File("src/main/resources/test.parquet");
        processor.process(parquetWriter, parquetFile);
        var orcFile = new File("src/main/resources/test.orc");
        processor.process(orcWriter, orcFile);
        System.out.printf(
                """
                        * * * * * * * * * * * * * * * *
                        *  Размер обработанного файла *
                        * * * * * * * * * * * * * * * *
                        Было: 1 Гб
                        Parquet: %s б
                        ORC: %s б
                        %n""",
                parquetFile.length(),
                orcFile.length());

        var now = System.currentTimeMillis();
        parquetReader.read(parquetFile);
        var parquetReadTime = System.currentTimeMillis() - now;
        now = System.currentTimeMillis();
        orcReader.read(orcFile);
        var orcReadTime = System.currentTimeMillis() - now;
        System.out.printf(
                """
                        * * * * * * * * * * * *
                        * Время чтения файлов *
                        * * * * * * * * * * * *
                        Parquet: %s мс
                        ORC: %s мс
                        %n""",
                parquetReadTime,
                orcReadTime);
    }

}
