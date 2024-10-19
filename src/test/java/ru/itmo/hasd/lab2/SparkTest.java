package ru.itmo.hasd.lab2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class SparkTest {

    @TempDir
    private File temporaryFile;

    @Test
    void test() {

    }

}
