package tools;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import reps.ScRepInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class POIExcelReader {


    public static void main(String[] args) throws IOException, InvalidFormatException {
        List<ScRepInfo> scRepInfos = ExcelGameInfo("src/main/resources/所有对局的建造列表(Rep目录下).xlsx ");
        for (int i = 0; i <scRepInfos.size(); i++) {
            ScRepInfo info = scRepInfos.get(i);
            System.out.println(info.toString());
        }
    }
    public static List<ScRepInfo> ExcelGameInfo(String excelFilePath) throws IOException, InvalidFormatException {

        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // 读取第一个工作表
        int rowNum = 0;
        List<ScRepInfo> infos = new ArrayList<>();

        for (Row row : sheet) {
            // 从第二行开始读取，跳过标题行，并只读取偶数行（根据实际情况调整）
            if (rowNum >= 1 && rowNum % 2 == 1) {

                Cell cell2 = row.getCell(1); // 第2列
                Cell cell4 = row.getCell(3); // 第4列
                Cell cell5 = row.getCell(4); // 第5列
                Cell cell6 = row.getCell(5); // 第6列
                Cell cell10 = row.getCell(9); // 第6列
                ScRepInfo info = new ScRepInfo();
                Integer youxiTime = minToSec(cell5.getStringCellValue());
                String FilePAth = cell10.getStringCellValue();
                String race = cell4.getStringCellValue();
                String mapName = cell2.getStringCellValue();
                String win = cell6.getStringCellValue();


                long EndTime = ReadFileTime.unixTime(FilePAth);
                long Startime = EndTime - youxiTime;


                    SimpleDateFormat yMd = new SimpleDateFormat("yyyy年MM月/dd/");
                    SimpleDateFormat yMd2 = new SimpleDateFormat("yyyy年MM月dd日");
                 SimpleDateFormat Hm = new SimpleDateFormat("HH点mm");
                String date = yMd.format(new Date(Startime * 1000));
                String date2 = yMd2.format(new Date(Startime * 1000));
                String min1 = Hm.format(new Date(Startime * 1000));
                String min2 = Hm.format(new Date(EndTime * 1000));

                String newPath = "/reps/"+date + race +"("+ win +")"+ "/" +min1+"-"+min2+mapName+"/";
                String newName = date2 + min1+"-"+min2+".SC2Replay";

                info.setMapName(mapName);
                info.setRace(race);
                info.setGameTime(youxiTime);
                info.setWin(win);
                info.setFilePAth(FilePAth);
                info.setEnDTime((int)EndTime);
                info.setStartTime((int)Startime);
                info.setNewPath(newPath);
                info.setNewName(newName);


                infos.add(info);
            }

            rowNum++;
        }

        inputStream.close();
        return infos;
    }



            public static Integer minToSec(String time) {
            // 假设时间格式是 "分钟:秒"


            // 分割字符串
            String[] parts = time.split(":");

            // 分别解析分钟和秒
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);

            // 转换总秒数


            return minutes * 60 + seconds;
        }


}
