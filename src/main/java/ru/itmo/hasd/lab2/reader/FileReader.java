package ru.itmo.hasd.lab2.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.File;

public interface FileReader {

    Dataset<Row> read(File file);

}
