package ru.itmo.hasd.lab2.config;

import lombok.experimental.UtilityClass;
import org.apache.spark.sql.SparkSession;

@UtilityClass
public class SparkConfig {

    private static final SparkSession INSTANCE = SparkSession.builder()
            .appName("SparkTestApp")
            .master("local")
            .getOrCreate();

    public static SparkSession getSparkSession() {
        return INSTANCE;
    }

}
