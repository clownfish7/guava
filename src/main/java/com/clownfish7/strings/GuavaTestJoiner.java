package com.clownfish7.strings;

import com.google.common.base.Joiner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author yzy
 * @classname GuavaTestJoiner
 * @description TODO
 * @create 2020-03-09 3:04 PM
 */
public class GuavaTestJoiner {
    static final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );
    static final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );

    public static void main(String[] args) {

        System.out.println(Joiner.on("#").join(stringList));
        // Google#Guava#Java#Scala#Kafka
        System.out.println(Joiner.on("#").skipNulls().join(stringListWithNullValue));
        // Google#Guava#Java#Scala
        System.out.println(Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue));
        // Google#Guava#Java#Scala#DEFAULT

        append();
        append2file();
    }

    public static void append() {
        StringBuilder stringBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(new StringBuilder(), stringListWithNullValue);
        Joiner.on("!").useForNull("DEFAULT").appendTo(stringBuilder, stringListWithNullValue);
        System.out.println(stringBuilder.toString());
        // Google#Guava#Java#Scala#DEFAULTGoogle!Guava!Java!Scala!DEFAULT
    }

    public static void append2file(){

        try ( FileWriter fw = new FileWriter(new File("D:\\xixi.txt"),true)){
            Joiner.on("#").useForNull("DEFAULT").appendTo(fw, stringListWithNullValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
