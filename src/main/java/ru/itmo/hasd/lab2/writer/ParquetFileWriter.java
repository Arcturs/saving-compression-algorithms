package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;

import java.io.File;

public class ParquetFileWriter implements FileWriter {

    @Override
    public void write(Dataset<String> data, File file) {
        data.write().parquet(file.getPath());
    }

}
