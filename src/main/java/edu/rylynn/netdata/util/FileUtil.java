package edu.rylynn.netdata.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> getAllFile(String dir){
        List<String> fileList = new ArrayList<>();

        File file = new File(dir);
        File[] files = file.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f.getAbsolutePath());
            } else if (f.isDirectory()) {
                fileList.addAll(getAllFile(f.getAbsolutePath()));
            }
        }

        return fileList;
    }
}
