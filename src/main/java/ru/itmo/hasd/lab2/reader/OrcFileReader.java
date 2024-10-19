package ru.itmo.hasd.lab2.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import ru.itmo.hasd.lab2.config.SparkConfig;

import java.io.File;

public class OrcFileReader implements FileReader {

    @Override
    public Dataset<Row> read(File file) {
        var spark = SparkConfig.getSparkSession();
        return spark.read().orc(file.getPath());
    }

}
