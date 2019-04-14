package bf.nt;

import bf.Global;

import java.io.PrintStream;
import java.util.Scanner;

public class UserVideosCrawlerCleaner {

    //REMOVE ALL ","
    public static void main(String[] args) {
        Scanner scanner = Global.getFileScanner("UserVideosBasic-1555232296771.csv");
        if (scanner == null) {
            return;
        }

        PrintStream output = Global.getFilePrintStream("UserVideosBasicCleaned-1555232296771.csv");
        if (output == null) {
            return;
        }

        output.println(scanner.nextLine());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String[] newParts = new String[7];
            for (int i=0;i<=2;i++) {
                newParts[i] = parts[i];
            }
            for (int i=0;i<=2;i++) {
                newParts[7-i-1] = parts[parts.length-i-1];
            }

            newParts[3] = parts[3];
            for (int i=4; i<=parts.length-3-1; i++) {
                newParts[3] += "，" + parts[i];
            }
            output.print(newParts[0]);
            for (int i=1;i<7;i++) {
                output.print("," + newParts[i]);
            }
            output.println();
        }

        output.flush();
        output.close();
    }

}
