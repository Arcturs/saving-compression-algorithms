package ru.itmo.hasd.lab2.processor;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import ru.itmo.hasd.lab2.config.SparkConfig;

import static org.apache.spark.sql.functions.when;
import static ru.itmo.hasd.lab2.constants.Constants.BOOKS_RATING_FILE_PATH;

public class BookRatingProcessor {

    public Dataset<Row> process() {
        var sparkSession = SparkConfig.getSparkSession();
        Dataset<Row> names = sparkSession.read()
                .schema(
                        DataTypes.createStructType(new StructField[]{
                                DataTypes.createStructField("Id", DataTypes.StringType, true),
                                DataTypes.createStructField("Title", DataTypes.StringType, true),
                                DataTypes.createStructField("Price", DataTypes.StringType, true),
                                DataTypes.createStructField("User_id", DataTypes.StringType, true),
                                DataTypes.createStructField("profileName", DataTypes.StringType, true),
                                DataTypes.createStructField("review/helpfulness", DataTypes.StringType, true),
                                DataTypes.createStructField("review/score", DataTypes.StringType, true),
                                DataTypes.createStructField("review/time", DataTypes.StringType, true),
                                DataTypes.createStructField("review/summary", DataTypes.StringType, true),
                                DataTypes.createStructField("review/text", DataTypes.StringType, true)
                        }))
                .csv(BOOKS_RATING_FILE_PATH);
        var ratingsCount = names.col("review/score");
        names.withColumn("ratingsCount", when(ratingsCount.isNaN(), "0.0").otherwise(ratingsCount));
        return names;
    }

}
