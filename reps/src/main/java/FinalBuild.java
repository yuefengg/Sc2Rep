import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import reps.ScRepInfo;
import tools.POIExcelReader;
import tools.subPathString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/***
 * 最终方法
 */
public class FinalBuild {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        // 假设SC2RepAnalysis文件夹位于用户的主目录下
        String tempPath = "";
        String Path =tempPath+"SC2RepAnalysis/所有对局的建造列表(rep目录下).xlsx";
        List<ScRepInfo> scRepInfos = POIExcelReader.ExcelGameInfo(Path);
        for (int i = 0; i < scRepInfos.size(); i++) {
            ScRepInfo scRepInfo = scRepInfos.get(i);
            String filePAth = scRepInfo.getFilePAth();
            String newName = scRepInfo.getNewName();
            String newPath = scRepInfo.getNewPath();
             newPath = Paths.get(filePAth).getParent() + newPath;
            String filename = subPathString.getParent(filePAth)+"\\SC2RepAnalysis\\"+ subPathString.getFileName(filePAth) + "/"+subPathString.getFileName(filePAth)+ ".txt";

            Files.createDirectories(Paths.get(newPath));
            Sc2BuildToTxt.toTxt(filename, tempPath+"replace.txt",newPath);

            Files.move(Paths.get(filePAth), Paths.get(newPath+newName));

        }
//        RemoveFolderSuffix.DelSuffix(Path);
//        SC2ReplayOrganizer.Organizer(Path);

        // 调用处理方法
//        processTxtFiles(Path);
    }

    private static void processTxtFiles(String directoryPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(FinalBuild::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 定义你想对每个.txt文件执行的方法
    private static void processFile(Path file) {
        String string = file.toString();
//        System.out.println("fu"+);
        Sc2BuildToTxt.toTxt(string, "replace.txt",file.getParent().toString());
    }





}
