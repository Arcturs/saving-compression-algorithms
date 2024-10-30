package ru.itmo.hasd.lab2.processor;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import ru.itmo.hasd.lab2.config.SparkConfig;

import static org.apache.spark.sql.functions.when;
import static ru.itmo.hasd.lab2.constants.Constants.BOOKS_DATA_FILE_PATH;

public class BookDataProcessor {

    public Dataset<Row> process() {
        var sparkSession = SparkConfig.getSparkSession();
        Dataset<Row> names = sparkSession.read()
                .schema(
                        DataTypes.createStructType(new StructField[]{
                                DataTypes.createStructField("Title", DataTypes.StringType, true),
                                DataTypes.createStructField("description", DataTypes.StringType, true),
                                DataTypes.createStructField("authors", DataTypes.StringType, true),
                                DataTypes.createStructField("image", DataTypes.StringType, true),
                                DataTypes.createStructField("previewLink", DataTypes.StringType, true),
                                DataTypes.createStructField("publisher", DataTypes.StringType, true),
                                DataTypes.createStructField("publishedDate", DataTypes.StringType, true),
                                DataTypes.createStructField("infoLink", DataTypes.StringType, true),
                                DataTypes.createStructField("categories", DataTypes.StringType, true),
                                DataTypes.createStructField("ratingsCount", DataTypes.DoubleType, true)
                        }))
                .csv(BOOKS_DATA_FILE_PATH);
        var ratingsCount = names.col("ratingsCount");
        names.withColumn("ratingsCount", when(ratingsCount.isNaN(), "0.0").otherwise(ratingsCount.multiply(100)));
        return names;
    }

}
