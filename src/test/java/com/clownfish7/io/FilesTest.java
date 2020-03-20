package com.clownfish7.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yzy
 * @classname FilesTest
 * @description TODO
 * @create 2020-03-18 11:04 AM
 */
public class FilesTest {

    final String sourceFile = "E:\\you\\projects\\guava\\src\\test\\resources\\source.txt";
    final String targetFile = "E:\\you\\projects\\guava\\src\\test\\resources\\target.txt";

    @Test
    public void testFilesCopy() throws IOException {
        Files.copy(new File(sourceFile), new File(targetFile));
    }

    @AfterEach
    public void tearDown() throws IOException {
        java.nio.file.Files.deleteIfExists(Paths.get(targetFile));
    }

    @Test
    public void testFilesMove() throws IOException {
        Files.move(new File(sourceFile), new File(targetFile));
    }

    @Test
    public void testReadString() {
        try {
            List<String> lines = Files.readLines(new File(sourceFile), Charsets.UTF_8);
            String result = Joiner.on("#").join(lines);
            Assertions.assertEquals(result, "hello hi halo#java guava#c c++");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToProcessString() throws IOException {
        List<Integer> result = Files.asCharSource(new File(sourceFile), Charsets.UTF_8).readLines(new LineProcessor<List<Integer>>() {

            List<Integer> result = new ArrayList<>();

            @Override
            public boolean processLine(String s) throws IOException {
                result.add(s.length());
                return s.length() > 0;
            }

            @Override
            public List<Integer> getResult() {
                return result;
            }
        });
        System.out.println(result);
    }

    @Test
    public void testFileMd5() throws IOException {
        System.out.println(Files.asByteSource(new File(sourceFile)).hash(Hashing.goodFastHash(128)));
    }

    @Test
    public void testFileSha() throws IOException {
        System.out.println(Files.asByteSource(new File(sourceFile)).hash(Hashing.sha256()));
    }

    @Test
    public void testFileWriter() throws IOException {
        Files.asCharSink(new File(sourceFile), Charsets.UTF_8, FileWriteMode.APPEND).write("hello");
        Files.write("".getBytes(Charsets.UTF_8), new File(sourceFile));
    }

    @Test
    public void testRecursive() {
        File file = new File("E:\\you\\projects\\guava\\src\\main");
        LinkedList<String> fileList = new LinkedList<>();
        recursive(file,fileList);
        fileList.forEach(System.out::println);
    }

    private void recursive(File root, List<String> fileList) {
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (File file : files) {
                recursive(file, fileList);
            }
        } else {
            fileList.add(root.getPath());
        }
    }

    @Test
    public void testFilesTree() {
        Iterable<File> files = Files.fileTraverser().breadthFirst(new File("E:\\you\\projects\\guava\\src\\main"));
        files.forEach(System.out::println);
    }
}
