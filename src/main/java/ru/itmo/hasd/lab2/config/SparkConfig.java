package ru.itmo.hasd.lab2.config;

import lombok.experimental.UtilityClass;
import org.apache.spark.sql.SparkSession;

@UtilityClass
public class SparkConfig {

    private static final SparkSession INSTANCE = SparkSession.builder()
            .appName("SparkTestApp")
            .config("spark.jars", "build/libs/saving-compression-algorithms-1.0-SNAPSHOT.jar")
            .master("spark://localhost:7077")
            .getOrCreate();

    public static SparkSession getSparkSession() {
        return INSTANCE;
    }

}
