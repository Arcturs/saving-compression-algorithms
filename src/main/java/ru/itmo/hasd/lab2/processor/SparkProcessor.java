package ru.itmo.hasd.lab2.processor;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import ru.itmo.hasd.lab2.config.SparkConfig;
import ru.itmo.hasd.lab2.writer.FileWriter;

import java.io.File;

public class SparkProcessor {

    public void process(FileWriter writer, File file, File newFile) {
        var sparkSession = SparkConfig.getSparkSession();
        Dataset<Row> names = sparkSession.read().json(file.getPath());
        Dataset<String> fullNames = names.map(
                (MapFunction<Row, String>) row -> {
                    var firstName = row.<String>getAs("firstName");
                    var surname = row.<String>getAs("surname");
                    return firstName + " " + surname;
                },
                Encoders.STRING());
        writer.write(fullNames, newFile);
    }

}
