package com.df.core;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFilterHelper {

    public static void main(String[] args) {
        String appdirPath = "E:\\workspace\\rights\\target\\rights-0.0.1-SNAPSHOT\\";


        List<String> keepFileList = new ArrayList<String>();
        keepFileList.add("WEB-INF\\view\\orderRights\\detail.jsp");
        keepFileList.add("WEB-INF\\view\\customer\\rights\\orderHotel.jsp");
        keepFileList.add("com\\rights\\api\\controller\\ApiTicketController.class");
        keepFileList.add("com\\rights\\order\\service\\impl\\OrderRightsServiceImpl.class");
        keepFileList.add("com\\rights\\order\\model\\OrderRights.class");
        keepFileList.add("com\\rights\\order\\mapper\\OrderRightsMapper.xml");


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
                        System.out.println("relativePath:" + relativePath);

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
                            // 当前目录下是否还有文件,没有则将当前目录删除
                        } else {
                            System.out.println("保留:" + relativePath);
                        }
                    }
                }
            }
        }
    }

}
