package edu.rylynn.netdata.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    public static void writeIntoBinaryFile(String filename, byte[] data){
        System.out.println("Begin to write file: "  + filename + "...");
        try
        {
            DataOutputStream out=new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(filename,true)));
            out.write(data);
            System.out.println(out.size()+" bytes have been written.");
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("File "  + filename + " write finish...");
    }

}
