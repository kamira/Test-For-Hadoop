package org.myorg;

import java.io.IOException;
import java.util.*;
 
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class Average {

  public static class Map extends MapReduceBase implements 
  Mapper<LongWritable, Text, Text, Text> {

    private Text word = new Text("Average");

    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
      

      String line = value.toString();
      StringTokenizer tokenizer = new StringTokenizer(line);
      
      while (tokenizer.hasMoreTokens()) {
        output.collect( word , new Text(tokenizer.nextToken()+ ":1"));
      }
    }
  }

  public static class Combine extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
      int sum = 0;
      int cnt = 0;
      while (values.hasNext()) {
        String str = values.next().toString();
        String[] array = str.split(":");

        sum += Integer.parseInt(array[0]);
        cnt += Integer.parseInt(array[1]);

      }
      output.collect( key , new Text(sum + ":" + cnt));
    }
  }

  public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, DoubleWritable> {
    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
      int sum = 0;
      int cnt = 0;
      while (values.hasNext()) {
        String str = values.next().toString();
        String[] array = str.split(":");

        sum += Integer.parseInt(array[0]);
        cnt += Integer.parseInt(array[1]);

      }
      double ave = (sum*1.0) / cnt ;
      output.collect( key , new DoubleWritable(ave));
    }
  }

  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(Average.class);
    conf.setJobName("Average");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Combine.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}
