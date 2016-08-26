/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.pattern;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

/**
 * @title PatternHomework
 * @desc this is my pattern homework which collects PO information from tianxiao-service using regex
 * @author hongyan
 * @date 2016年8月3日
 * @version version
 */
public class PatternHomework {
    private static BufferedReader reader;

    private static List<File> getAllFiles(String folderPath) throws FileNotFoundException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new FileNotFoundException("fodlerPath is not a right path or folder!");
        }
        List<File> files = Lists.newLinkedList();
        getFilesByRecursion(folder, files);
        return files;
    }

    private static void getFilesByRecursion(File folder, List<File> files) {
        if (folder.isDirectory()) {
            File[] subFiles = folder.listFiles();
            for (File f : subFiles) {
                getFilesByRecursion(f, files);
            }
        } else if (folder.isFile()) {
            if (folder.getName().endsWith(".java"))// only pick java file
                files.add(folder);
        }
    }

    private static String getContent(File f) {
        final StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static List<String> getPOString(List<File> files, Pattern tablePattern, Pattern entityPattern) {
        List<String> result = Lists.newArrayList();
        StringBuilder sb = null;
        Matcher tableMatcher = null;
        Matcher entityMatcher = null;
        Iterator<File> iter = files.iterator();
        while (iter.hasNext()) {
            File file = iter.next();
            String content = getContent(file);
            entityMatcher = entityPattern.matcher(content);
            tableMatcher = tablePattern.matcher(content);
            boolean entityFlag = entityMatcher.find();
            boolean tableFlag = tableMatcher.find();
            if (entityFlag || tableFlag) {// bug:这里不能用短路逻辑
                sb = new StringBuilder();
                sb.append(file.getName());
                if (entityFlag) {
                    System.out.println(file.getName() + "\t" + entityMatcher.group());
                    sb.append(entityMatcher.group());
                }
                if (tableFlag) {
                    System.out.println(file.getName() + "\t" + tableMatcher.group());
                    sb.append(tableMatcher.group());
                }
                result.add(sb.toString());
                System.out.println();
            }
        }
        return result;
    }

    @Data
    private static class POInfo {
        private String javaName;
        private String catalog;
        private String name;
    }

    private static POInfo convertStrTOPOInfo(String str, Pattern javaPattern, Pattern catalogPattern,
        Pattern namePattern) {
        POInfo po = new POInfo();
        Matcher mat1 = javaPattern.matcher(str);
        Matcher mat2 = catalogPattern.matcher(str);
        Matcher mat3 = namePattern.matcher(str);
        mat1.find();
        mat2.find();
        mat3.find();
        po.setJavaName(mat1.group(1));
        po.setName(mat2.group(1));
        po.setCatalog(mat3.group(1));
        return po;
    }

    public static void main(String[] args) throws IOException {
        Pattern tablePattern = Pattern.compile("@[ ]*Table[ ]*\\(*.*\\)*");// @Table识别正则
        Pattern entityPattern = Pattern.compile("@[ ]*Entity[ ]*\\(*.*\\)*");// @Entity识别正则
        List<File> files = getAllFiles("/Users/hongyan/Documents/workspace/tianxiao-service");// 扫描路径

        System.out.println("待扫描的文件数量:" + files.size());
        List<String> list = getPOString(files, tablePattern, entityPattern);// 正则抓取指定文件内容
        System.out.println("属于PO的文件数量:" + list.size());

        Pattern javaPattern = Pattern.compile("^(\\w+)*\\.java");// 类名识别正则
        Pattern catalogPattern = Pattern.compile("name[ ]*=[ ]*\"[ ]*(\\w+)[ ]*\"");// 数据库名识别正则
        Pattern namePattern = Pattern.compile("catalog[ ]*=[ ]*\"[ ]*(\\w+)[ ]*\"");// 表名识别正则

        List<POInfo> pos = Lists.newArrayList();
        for (String str : list) {
            pos.add(convertStrTOPOInfo(str, javaPattern, catalogPattern, namePattern));// 字符串转化实体类
        }
        
        Collections.sort(pos,Ordering.natural().onResultOf(new Function<POInfo, Integer>() {
            @Override
            public Integer apply(POInfo input) {
                return input.toString().length();
            }
        }));

        writeIntoFile(pos, "/Users/hongyan/Desktop/javaTestFile");// 写文件

    }

    private static void writeIntoFile(List<POInfo> pos, String string) {
        BufferedWriter bos;
        try {
            bos = new BufferedWriter(new FileWriter(string));
            for (POInfo po : pos) {
                String line = "类名:" + po.getJavaName() + "\t 数据库:" + po.getCatalog() + "\t 表名:" + po.getName();
                System.out.println(line);
                bos.write(line);
                bos.newLine();
            }
            bos.close();
        } catch (IOException e) {
            System.out.println("指定目录无法写入!");
            e.printStackTrace();
        }
        System.out.println("写入文件done!");

    }

}
