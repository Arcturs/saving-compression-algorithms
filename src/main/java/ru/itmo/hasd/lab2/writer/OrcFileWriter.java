package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;

import java.io.File;

public class OrcFileWriter implements FileWriter {

    @Override
    public void write(Dataset<String> data, File file) {
        data.write().orc(file.getPath());
    }

}
