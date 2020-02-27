package com.withing.java.time;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/**
 * @author huangweixin7
 * @date 2020/2/17 21:50:05
 * description: IOWriter
 */
public class IOWriterReader {

    @Test
    public void fileWriter() {
        try {
            Writer fileWriter = new FileWriter("writerFile");
            int i = 100;
            while (i-- > 0) {
                fileWriter.append(getRandomString(1000));
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileReader() {
        String str = null;
        try {
            Reader reader = new FileReader("writerFile");
            char[] chars = new char[1000];
            StringBuilder builder = new StringBuilder();
            while (reader.read(chars) != -1) {
                builder.append(chars);
            }
            reader.close();
            str = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(str);
    }

    @Test
    public void bufferWriter() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("bufferedFile"));
            int i = 100;
            while (i-- > 0) {
                bufferedWriter.write(getRandomString(1000));
                bufferedWriter.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bufferReader() {
        String str = null;
        String temp = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("bufferedFile"));
            StringBuilder builder = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                builder.append(temp);
            }
            str = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(str);
    }

    @Test
    public void fileInputStream() {
        String str = null;
        try {
            InputStream inputStream = new FileInputStream(new File("bufferedFile"));
            StringBuilder builder = new StringBuilder();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                builder.append(new String(buff, StandardCharsets.UTF_8));
            }
            str = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(str);
    }

    @Test
    public void fileOutputStream() {

    }

    @Test
    public void compareBufferCostTime() {
        Instant before = Instant.now();
        fileWriter();
        Instant after = Instant.now();
        Duration commonCost = Duration.between(before, after);
        System.out.println("commonCost:  " + commonCost.getNano() + "ns");

        before = Instant.now();
        bufferWriter();
        after = Instant.now();
        Duration bufferedCost = Duration.between(before, after);
        System.out.println("bufferedCost: " + bufferedCost.getNano() + "ns");
    }


    public String getRandomString(int count) {
        return RandomStringUtils.random(count, true, true);
    }

}
