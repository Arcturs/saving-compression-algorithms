package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;

import java.io.File;

public class ParquetFileWriter implements FileWriter {

    @Override
    public void write(Dataset<String> data, File file) {
        data.write()
                .mode(SaveMode.Overwrite)
                .option("inferSchema", true)
                .option("header", true)
                .parquet(file.getAbsolutePath());
    }

}
