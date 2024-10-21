package ru.itmo.hasd.lab2.processor;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import ru.itmo.hasd.lab2.config.SparkConfig;
import ru.itmo.hasd.lab2.writer.FileWriter;

import java.io.File;

public class SparkProcessor {

    public void process(FileWriter writer, File newFile) {
        var sparkSession = SparkConfig.getSparkSession();
        var file = new File("src/main/resources/test.csv");
        Dataset<Row> names = sparkSession.read().csv(file.getAbsolutePath());
        Dataset<String> fullNames = names.map(
                (MapFunction<Row, String>) row -> {
                    var firstName = row.<String>getAs("_c0");
                    var lastName = row.<String>getAs("_c1");
                    return firstName + " " + lastName;
                },
                Encoders.STRING());
        writer.write(fullNames, newFile);
    }

}
