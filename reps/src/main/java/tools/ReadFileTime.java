package tools;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;
import java.nio.file.attribute.FileTime;

public class  ReadFileTime {
    public static Integer unixTime(String filepath) {
        // 指定文件路径
        Path path = Paths.get(filepath);

        try {
            // 读取文件属性
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            // 获取最后修改时间
            FileTime fileTime = attrs.lastModifiedTime();

            // 将FileTime转换为Unix时间戳（秒）
            Integer unixTime = (int) (fileTime.toMillis() / 1000);

            // 打印Unix时间戳
            return unixTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}