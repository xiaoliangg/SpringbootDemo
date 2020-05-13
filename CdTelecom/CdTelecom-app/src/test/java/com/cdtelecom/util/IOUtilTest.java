package com.cdtelecom.util;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOUtilTest {

    @Test
    public void testExportCsv(){
        File f = new File("C:\\Users\\ll\\Desktop\\1.txt");
        List<String> dataList = new ArrayList<>();
        dataList.add("2");
        IOUtil.exportCsv(f,dataList);
    }

    @Test
    public void testImportCsv(){
        File f = new File("C:\\Users\\ll\\Desktop\\1.txt");
        List<String> dataList = IOUtil.importCsv(f);
        System.out.println("文件首行数据:" + dataList.get(0));
    }
}
