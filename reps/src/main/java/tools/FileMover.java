package tools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

public class FileMover {

    /**
     * 移动文件到指定目录并重命名
     * @param sourceFilePath 源文件路径
     * @param targetDirPath 目标目录路径
     * @param newFileName 新文件名
     * @throws IOException 如果移动文件时发生错误
     */
    public static void moveFileAndRename(String sourceFilePath, String targetDirPath, String newFileName) throws IOException {
        // 构建源路径和目标路径
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetDirPath, newFileName);

        // 移动文件
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("文件已移动并重命名为: " + targetPath);
    }


}
