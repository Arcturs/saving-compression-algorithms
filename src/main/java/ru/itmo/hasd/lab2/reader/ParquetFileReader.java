package ru.itmo.hasd.lab2.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import ru.itmo.hasd.lab2.config.SparkConfig;

public class ParquetFileReader implements FileReader {

    @Override
    public Dataset<Row> read(String path) {
        var spark = SparkConfig.getSparkSession();
        return spark.read()
                .parquet(path);
    }

}
