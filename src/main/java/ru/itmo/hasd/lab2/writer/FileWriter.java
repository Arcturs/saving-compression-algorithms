package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;

import java.io.File;

public interface FileWriter {

    void write(Dataset<String> data, File file);

}
