import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sc2BuildToTxt {


    public static void toTxt(String inputPath, String replacePath,String outPath) {


        List<String> player1 = new ArrayList<>();
        List<String> player2 = new ArrayList<>();

        txtToZerg(inputPath, replacePath, 20, player1, player2);
//        for (int i = 0; i < player1.size(); i++) {
//            System.out.println("player1->" + player1.get(i));
//        }
//        for (int i = 0; i < player2.size(); i++) {
//            System.out.println("player2->" + player2.get(i));
//        }
        String fileName1 = outPath+"/player1.txt";
        String fileName2 = outPath+"/player2.txt";


        writeToFile(player1, fileName1);
        writeToFile(player2, fileName2);

        File file = new File(inputPath);
//        file.delete();

    }


    public static void txtToZerg(String inputPath, String replacePath, int maxlength, List<String> player1, List<String> player2) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedReader ignoreReader = new BufferedReader(new FileReader(inputPath));
        ) {
            String time;
            String workers;
            String build;
            String newline;
            String line;
            reader.readLine();
            Map<String, String> replacements = readReplacementsFromFile(replacePath);
            boolean flag = true;
            while ((line = reader.readLine()) != null) {


                if (!line.startsWith("--")) {
                    if (!line.startsWith("[")) {

                        continue;
                    }
                    String str = replaceText(line, replacements);
                    if (str == null) {
                        return;
                    }
                    // 移除字符串开头的 '[' 和末尾的 ']'（如果总是存在）
                    str = str.substring(1);

                    // 使用 '|' 作为分隔符拆分字符串
                    String[] parts = str.split("\\|");

                    // 遍历并打印每个部分
                    time = str.substring(0, parts[0].length() - 1);
                   try {
                       workers = parts[1].substring(2);
                   }catch (Exception e) {
                       System.out.println("err--->["+str+"<----err");
                       continue;

                   }



                    build = parts[2].substring(1);
                    if (!isLastCharComma(build)) {
                        build = build.substring(0, build.length() - 1);
                    }
                    build = removeLastTwoIfLastIsOne(build);


                    if (!build.startsWith("*") && !build.isEmpty()&&!build.startsWith(",")) {
                        newline = "[" + formatTime(time) + "]" + workers + " " + formatTime(time).substring(0, formatTime(time).length() - 3) + " " + formatStringWithSpaces(build, maxlength);
//                        System.out.println(newline);
                        if (!newline.matches(".*[\u4e00-\u9fa5].*")){
                            continue;
                        }
                        if (flag) {

                            player1.add(newline);
                        } else {
                            player2.add(newline);
                        }
                    }
                } else {
                    flag = false;
                }


//            String modifiedLine = line.replaceAll("oldText", "newText");

                // 将修改后的行写入到新文件
//            writer.write(modifiedLine);
//            writer.newLine(); // 不要忘记添加新行符

//            }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatTime(String timeStr) {
        // 检查输入字符串是否为null或空
        if (timeStr == null || timeStr.isEmpty()) {
            return null; // 或者抛出一个异常
        }

        // 使用":"分割字符串为分钟和秒
        String[] parts = timeStr.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected 'mm:ss' but got: " + timeStr);
        }

        // 尝试将分钟和秒解析为整数
        try {
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts.length > 1 ? parts[1] : "0"); // 如果秒部分为空，则默认为0

            // 格式化分钟和秒，并添加默认的毫秒部分（.00）
            String formattedTime = String.format("%02d:%02d.00", minutes, seconds);
            return formattedTime;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time format. Non-numeric values found: " + timeStr, e);
        }
    }


    public static String formatStringWithSpaces(String input, int maxLengthInSpaces) {
        // 初始化空格计数器
        int totalSpaces = 0;

        // 遍历输入字符串，计算其“空格等效长度”
        for (char c : input.toCharArray()) {
            if (isChinese(c)) {
                // 如果是中文字符，则增加2个空格的长度
                totalSpaces += 2;
            } else if (Character.isDigit(c) || isSpecialCharacter(c)) {
                // 如果是数字或特殊字符，则增加1个空格的长度
                totalSpaces++;
            }
            // 对于其他字符，这里假设它们不增加空格长度，但可以根据需要调整
        }

        // 计算需要添加的空格数以达到最大长度
        int spacesToAdd = maxLengthInSpaces - totalSpaces;

        // 如果需要添加的空格数为负数，则截断原始字符串或抛出异常
        // 这里我们选择截断原始字符串（如果需要）
//        if (spacesToAdd < 0) {
//            // 截断逻辑可以根据具体需求实现，这里简单示例为截断到最大长度允许的范围
//            // 注意：这里并没有真正实现截断，因为截断逻辑可能更复杂（如基于字符边界）
//            // 实际应用中，你可能需要更精细地处理这种情况
//            // 这里仅作为演示，我们直接返回原始字符串（如果它足够短）或空字符串（如果它太长）
//            // 但更好的做法可能是抛出一个异常或返回一个错误消息//            throw new IllegalArgumentException("Input string is too long to fit within the maximum length.");
//        }

        // 生成并返回填充了空格的字符串
        // 注意：这里简单地在字符串末尾添加空格，但实际应用中可能需要更复杂的逻辑
        // 比如，如果需要在特定位置添加空格，或者保持某些字符的相对位置不变
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < spacesToAdd; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    // 辅助方法，用于检查字符是否为特殊字符
    // 这里简单地将"*", ","视为特殊字符，但你可以根据需要扩展这个列表
    private static boolean isSpecialCharacter(char c) {
        return c == '*' || c == ',';
    }

    public static boolean isChinese(char c) {
        // 字符c的Unicode码点
        int codePoint = Character.codePointAt(String.valueOf(c), 0);
        // 检查是否在基本汉字区块内
        return codePoint >= 0x4E00 && codePoint <= 0x9FFF;
    }

    public static Map<String, String> readReplacementsFromFile(String replacePath) {
        Map<String, String> replacements = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(replacePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2); // 使用limit参数2来避免将逗号内的逗号也分割
                if (parts.length == 2) {
                    replacements.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return replacements;
    }

    // 使用给定的替换规则替换字符串中的文本
    public static String replaceText(String text, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            if (text != null) {
                text = text.replace(entry.getKey(), entry.getValue());
            }

        }
        return text;
    }

    /**
     * 检查字符串的最后一位是否是逗号。
     *
     * @param str 要检查的字符串。
     * @return 如果字符串的最后一位是逗号，则返回true；否则返回false。
     */
    public static boolean isLastCharComma(String str) {
        // 检查字符串是否为空或长度为0
        if (str == null || str.isEmpty()) {
            return false; // 或者你可以根据需求抛出异常或返回特定的值
        }

        int codePointAt = Character.codePointAt(String.valueOf(str.charAt(str.length() - 1)), 0);
        return codePointAt >= 0x4E00 && codePointAt <= 0x9FFF;
    }

    /**
     * 如果字符串的最后一位是数字'1'，则删除最后两位。
     *
     * @param str 输入的字符串。
     * @return 修改后的字符串。
     */
    public static String removeLastTwoIfLastIsOne(String str) {
        // 检查字符串是否为空或长度小于2
        if (str == null || str.length() < 2) {
            return str; // 或者抛出异常，根据你的需求而定
        }

        // 检查最后一位是否是'1'
        if (str.charAt(str.length() - 1) == '1') {
            // 如果是，则删除最后两位
            return str.substring(0, str.length() - 2);
        }

        // 如果不是，则返回原始字符串
        return str;
    }

    /**
     * 将字符串数组写入到指定的文件中
     *
     * @param data     要写入文件的字符串数组
     * @param replacePath 文件的路径
     */
    public static void writeToFile(List<String> data, String replacePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(replacePath);
            for (String line : data) {
                writer.write(line + "\n"); // 写入一行内容，‌并添加换行符
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close(); // 关闭文件写入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

