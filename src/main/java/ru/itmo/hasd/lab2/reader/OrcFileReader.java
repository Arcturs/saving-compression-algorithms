package ru.itmo.hasd.lab2.reader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.orc.OrcFile;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.IOException;

public class OrcFileReader implements FileReader {

    @Override
    public Dataset<Row> read(String path) {
        try {
            OrcFile.createReader(new Path("path"), OrcFile.readerOptions(new Configuration()))
                    .rows();
        } catch (IOException e) {}
        return null;
    }

}
