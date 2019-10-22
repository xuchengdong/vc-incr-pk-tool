package com.df.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileFilterHelper2 {

    public static void main(String[] args) throws IOException {
        List<String> keepFileList = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(FileFilterHelper2.class.getResourceAsStream("/version.txt")));
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

        String appdirPath = "C:\\Users\\26093\\workhome\\mall-platform-build-henan\\mall-platform-project\\mall-platform-portals\\mall-platform-backend\\target\\backend";
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
