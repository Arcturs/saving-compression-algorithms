package ru.itmo.hasd.lab2.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

public class OrcFileWriter implements FileWriter {

    @Override
    public void write(Dataset<Row> data, String path, String compressionType) {
        data.write()
                .mode(SaveMode.Overwrite)
                .option("inferSchema", true)
                .option("header", true)
                .option("compression", compressionType)
                .orc(path);
    }

}
