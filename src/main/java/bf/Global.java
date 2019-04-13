package bf;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Global {

    // https://space.bilibili.com/ajax/member/getSubmitVideos?mid=777536&pagesize=30&tid=0&page=1&keyword=&order=pubdate
    public static String usersBasicCSVV = "followings-1554013692072.csv";
    public static String top200TXT = "30plus.txt";
    public static String top30TXT = "100plus.txt";

    public static String httpGet(String urlStr) {
        String result = "";
        BufferedReader in = null;
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void deleteDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            for (File file: dir.listFiles()) {
                deleteDir(file);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

    public static Scanner getFileScanner(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "GBK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return scanner;
    }

    public static PrintStream getFilePrintStream(String path) {
        File file = new File(path);
        if (file.exists()) {
            Global.deleteDir(file);
        }

        boolean initIOSucc;
        PrintStream output = null;
        try {
            initIOSucc = file.createNewFile();
            output = new PrintStream(file, "GBK");
        } catch (IOException e) {
            e.printStackTrace();
            initIOSucc =false;
        }

        if (!initIOSucc) {
            return null;
        }

        return output;
    }
}
