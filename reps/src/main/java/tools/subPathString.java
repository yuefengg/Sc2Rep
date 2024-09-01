package tools;

import java.nio.file.Path;
import java.nio.file.Paths;

public class subPathString {

    public static void main(String[] args) {
        System.out.println(getFileName("C:/Users/YF/Desktop/Rep/20231216 - Game 1 - Cure vs ByuN - TvT - Site Delta.SC2Replay"));
    }
    public static String getFileName(String Spath) {

        // 使用Paths.get()从字符串创建Path对象
        Path path = Paths.get(Spath);

        // 使用getFileName()提取文件名部分
        Path fileName = path.getFileName();

        // 打印提取出的文件名

//        System.out.println("文件名: " + fileName.toString());
        return fileName.toString();

    }
    public static String getParent(String Spath) {

        // 使用Paths.get()从字符串创建Path对象
        Path path = Paths.get(Spath);

        // 使用getFileName()提取文件名部分
        Path fileName = path.getParent();

        // 打印提取出的文件名

//        System.out.println("文件名: " + fileName.toString());
        return fileName.toString();

    }
}


