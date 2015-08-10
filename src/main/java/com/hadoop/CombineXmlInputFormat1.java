package com.hadoop;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.*;

import java.io.IOException;

/**
 * Created by wangxy on 15-8-6.
 */
public class CombineXmlInputFormat1
        extends CombineFileInputFormat<LongWritable,Text> {


    public RecordReader<LongWritable,Text> createRecordReader(InputSplit split,
                                                              TaskAttemptContext context) throws IOException {
        return new CombineFileRecordReader<LongWritable,Text>(
                (CombineFileSplit)split, context, XmlRecordReaderWrapper.class);
    }

    private static class XmlRecordReaderWrapper
            extends CombineFileRecordReaderWrapper<LongWritable,Text> {
        // this constructor signature is required by CombineFileRecordReader
        public XmlRecordReaderWrapper(CombineFileSplit split,
                                       TaskAttemptContext context, Integer idx)
                throws IOException, InterruptedException {
            super(new xmlInputFormat(), split, context, idx);
        }
    }
}
