import java.io.File;

public class RemoveFolderSuffix {

    public static void DelSuffix(String path) {
        // 设定要处理的根目录，这里为当前目录
        File rootDir = new File(path);

        // 遍历当前目录及其子目录
        traverseAndRenameFolders(rootDir);
    }



    private static void traverseAndRenameFolders(File dir) {
        // 获取目录下的所有文件和文件夹
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                // 如果是文件夹且名称以.SC2Replay结尾
                if (file.isDirectory() && file.getName().endsWith(".SC2Replay")) {
                    // 构造新的文件夹名称，去除.SC2Replay后缀
                    String newName = file.getName().substring(0, file.getName().lastIndexOf(".SC2Replay"));
                    File newDir = new File(file.getParent(), newName);

                    // 重命名文件夹
                    boolean success = file.renameTo(newDir);

                    if (success) {
                        System.out.println("Renamed folder: " + file.getAbsolutePath() + " to " + newDir.getAbsolutePath());
                    } else {
                        System.err.println("Failed to rename folder: " + file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    // 如果是文件夹但不是目标后缀，则递归处理
                    traverseAndRenameFolders(file);
                }
                // 如果是文件，则不处理
            }
        }
    }
}