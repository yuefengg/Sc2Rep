import java.io.File;

public class RemoveFolderSuffix {

    public static void DelSuffix(String path) {
        // �趨Ҫ����ĸ�Ŀ¼������Ϊ��ǰĿ¼
        File rootDir = new File(path);

        // ������ǰĿ¼������Ŀ¼
        traverseAndRenameFolders(rootDir);
    }



    private static void traverseAndRenameFolders(File dir) {
        // ��ȡĿ¼�µ������ļ����ļ���
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                // ������ļ�����������.SC2Replay��β
                if (file.isDirectory() && file.getName().endsWith(".SC2Replay")) {
                    // �����µ��ļ������ƣ�ȥ��.SC2Replay��׺
                    String newName = file.getName().substring(0, file.getName().lastIndexOf(".SC2Replay"));
                    File newDir = new File(file.getParent(), newName);

                    // �������ļ���
                    boolean success = file.renameTo(newDir);

                    if (success) {
                        System.out.println("Renamed folder: " + file.getAbsolutePath() + " to " + newDir.getAbsolutePath());
                    } else {
                        System.err.println("Failed to rename folder: " + file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    // ������ļ��е�����Ŀ���׺����ݹ鴦��
                    traverseAndRenameFolders(file);
                }
                // ������ļ����򲻴���
            }
        }
    }
}