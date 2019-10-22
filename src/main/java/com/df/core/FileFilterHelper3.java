package com.df.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileFilterHelper3 {

    private static final String DEFAULT_PATH = "/file.conf";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (args == null || args.length < 1) {
            args = new String[2];

            System.out.println("请输入编译后的工程路径:");
            String arsStr = scanner.next();
            if (arsStr != null && !"".endsWith(arsStr.trim())) {
                args[0] = arsStr.trim();
            }
        }

//        if (args[1] == null || "".equals(args[1].trim())) {
//            System.out.println(String.format("请输入配置文件路径[默认当前目录下%s]:", DEFAULT_PATH));
//            String arsStr = scanner.next();
//            if (arsStr != null && !"".endsWith(arsStr.trim())) {
//                args[1] = arsStr.trim();
//            } else {
//                args[1] = DEFAULT_PATH;
//            }
//        }


        List<String> keepFileList = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(FileFilterHelper3.class.getResourceAsStream(DEFAULT_PATH)));
        String lineStr = null;
        while ((lineStr = reader.readLine()) != null) {
            int index = lineStr.lastIndexOf("src/main/java/");
            if (index != -1) {
                lineStr = lineStr.substring(index + 14);
            }

            index = lineStr.lastIndexOf("src/main/webapp/");
            if (index != -1) {
                lineStr = lineStr.substring(index + 16);
            }

            if (lineStr.endsWith(".java")) {
                lineStr = lineStr.replace(".java", ".class");
            }

            lineStr = lineStr.replace("/", "\\");
            keepFileList.add(lineStr);
        }

        String appdirPath = args[0];
        File appdir = new File(appdirPath);

        recursive(appdir, appdirPath, keepFileList);
    }


    public static void recursive(File appdir, String appdirPath, List<String> keepFileList) {
        if (appdir.exists() && appdir.isDirectory()) {
            File[] files = appdir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {

                        // 递归调用
                        recursive(file, appdirPath, keepFileList);

                        if (file.listFiles() == null || file.listFiles().length == 0) {
                            // 删除空目录
                            boolean flag = file.delete();
                            System.out.println("删除目录:" + file.getPath() + (flag ? "成功" : "失败"));
                        }
                    } else {
                        String absolutePath = file.getAbsolutePath();
                        String relativePath = absolutePath.replace(appdirPath, "");

                        boolean keep = false;
                        for (String filePath : keepFileList) {
                            if (relativePath.endsWith(filePath)) {
                                keep = true;
                                break;
                            }
                        }
                        if (!keep) {
                            boolean flag = file.delete();
                            System.out.println("删除:" + relativePath + (flag ? "成功" : "失败"));
                        } else {
                            System.out.println("保留:" + relativePath);
                        }
                    }
                }
            }
        }
    }

}
