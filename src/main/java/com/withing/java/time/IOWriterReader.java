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
        try (Writer fileWriter = new FileWriter("writerFile")) {
            int i = 100;
            while (i-- > 0) {
                fileWriter.append(getRandomString(1000));
                fileWriter.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileReader() {
        String str = null;
        try (Reader reader = new FileReader("writerFile")) {
            char[] chars = new char[1024];
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
    public void bufferedInputStream() {
        String str = null;
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("bufferedStream")))) {
            StringBuilder builder = new StringBuilder();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, StandardCharsets.UTF_8));
            }
            str = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bufferedOutputStream() {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File("bufferedStream")))) {
            int i = 100;
            while (i-- > 0) {
                outputStream.write(getRandomString(1000).getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bufferWriter() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("bufferedFile"))) {
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
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("bufferedFile"))) {
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
        try (InputStream inputStream = new FileInputStream(new File("bufferedFile"))) {
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

//        System.out.println(str);
    }

    @Test
    public void fileOutputStream() {
        try (OutputStream outputStream = new FileOutputStream(new File("streamFile"));) {
            int i = 100;
            while (i-- > 0) {
                outputStream.write(getRandomString(1000).getBytes());
                outputStream.write("\n".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compareBufferedStream() {
        calculateCostTime(this::bufferedOutputStream, "bufferedOutputStream");
        calculateCostTime(this::fileOutputStream, "fileOutputStream");
        calculateCostTime(()->{}, "empty");
    }

    @Test
    public void compareStreamWriter() {
        calculateCostTime(this::fileWriter, "fileWriter");
        calculateCostTime(this::fileOutputStream, "fileOutPutStream");
        System.out.println();
        calculateCostTime(this::fileReader, "fileReader");
        calculateCostTime(this::fileInputStream, "fileInputStream");
    }

    @Test
    public void compareBufferWriterCostTime() {
        calculateCostTime(this::fileWriter, "fileWriter");
        calculateCostTime(this::bufferWriter, "bufferedWriter");
        System.out.println();
        calculateCostTime(this::fileReader, "fileReader");
        calculateCostTime(this::bufferReader, "bufferFileReader");
    }

    private void calculateCostTime(Runnable runnable, String methodName) {
        Instant before = Instant.now();
        runnable.run();
        Instant after = Instant.now();
        Duration duration = Duration.between(before, after);
        System.out.println(methodName + " cost " + duration.getNano() + "ns");
    }


    public String getRandomString(int count) {
        return RandomStringUtils.random(count, true, true);
    }

}
