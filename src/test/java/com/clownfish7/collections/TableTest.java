package com.clownfish7.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

/**
 * @author You
 * @create 2020-04-06 17:13
 */
public class TableTest {

    // ArrayTable
    // TreeBasedTable
    // HashBasedTable
    // ImmutableTable

    @Test
    public void test() {
        HashBasedTable<Object, Object, Object> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Python", "3.7");
        table.put("Database", "Mysql", "5.7");
        table.put("Database", "Oracle", "12C");
        System.out.println(table);
        Map<Object, Object> language = table.row("Language");
        Assertions.assertTrue(language.containsKey("Java"));
        Map<Object, Object> column = table.column("Java");
        Assertions.assertTrue(column.containsKey("Language"));
        Set<Table.Cell<Object, Object, Object>> cells = table.cellSet();
        System.out.println(cells);
    }
}
