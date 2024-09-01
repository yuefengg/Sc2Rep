import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SC2ReplayOrganizer {
    public static void Organizer(String targetDirName) {
        // 当前工作目录
        String currentDir = System.getProperty("user.dir");

        File targetDir = new File(currentDir, targetDirName);

        // 如果目标文件夹不存在，则创建它
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdir();
            if (!created) {
                System.out.println("无法创建目标文件夹：" + targetDirName);
                return;
            }
        }

        // 获取当前目录下所有文件
        File[] files = new File(currentDir).listFiles();
        if (files != null) {
            for (File file : files) {
                // 检查文件是否以.SC2Replay结尾
                if (file.isFile() && file.getName().endsWith(".SC2Replay")) {
                    // 创建以文件名（不包括扩展名）命名的子文件夹
                    String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
                    File subDir = new File(targetDir, fileNameWithoutExtension);
                    if (!subDir.exists()) {
                        boolean created = subDir.mkdir();
                        if (!created) {
                            System.out.println("无法创建子文件夹：" + fileNameWithoutExtension);
                            continue;
                        }
                    }

                    // 移动文件到对应的子文件夹
                    File newFileLocation = new File(subDir, file.getName());
                    try {
                        Files.move(file.toPath(), newFileLocation.toPath());
                        System.out.println("文件 " + file.getName() + " 已成功移动到 " + subDir.getPath());
                    } catch (IOException e) {
                        System.out.println("移动文件时出错：" + e.getMessage());
                    }
                }
            }
        }
    }
}
