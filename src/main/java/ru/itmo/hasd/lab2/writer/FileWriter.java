package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface FileWriter {

    void write(Dataset<Row> data, String path, String compressionType);

}
