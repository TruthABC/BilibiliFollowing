package bf.nt;

import bf.Global;
import bf.entity.VideoTag;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.PrintStream;
import java.util.Scanner;

public class VideoTagCrawler {

    //http://api.bilibili.com/x/tag/archive/tags?aid=49025020
    public static final String BASE_URL_TAG = "http://api.bilibili.com/x/tag/archive/tags?aid=";

    private VideoTag mVideoTag;

    public void crawlFromDeailedWithVideoTagWhileSavingCSV(String fromCSV, String toCSV) {
        //Arrange local input
        Scanner scanner = Global.getFileScanner(fromCSV);
        if (scanner == null) {
            return;
        }

        //Arrange output
        PrintStream output = Global.getFilePrintStream(toCSV);
        if (output == null) {
            return;
        }
        output.print("mid,author,aid,title,created,length,pic");//7
        output.print(",duration,tid,tname");//7 + 3
        output.print(",view,danmaku,reply,like,coin,favorite");//7 + 3 + 6
        output.print(",tag_id,tag_name,use,atten");
        output.println();

        //input each line and crawl each line and save each line
        // remember to add spaces when needed
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            //suplliment print for empty parts
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            //e.g. only former 7 available
            // for (i = 7; i < 16; i++) -> 7 8 9 10 11 12 13 14 15 totaly 9 commas
            for (int i = parts.length; i < (7+3+6); i++) {
                line += ",";
            }

            //initialize global
            mVideoTag = new VideoTag(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Integer.parseInt(parts[2]),
                    parts[3]);
            try {
                crawlWhilePrint(mVideoTag, output, line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        output.flush();
        output.close();
    }

    private int mCounter = 0;
    private void crawlWhilePrint(VideoTag videoTag, PrintStream output, String former) throws Exception {
        System.out.println("[ " + mCounter++ + " ] aid: " + videoTag.getAid() + "; title: " + videoTag.getTitle());
        if (mCounter % 40 == 0) {
            Thread.sleep(1*60*1000);
        }

        // HTTP GET
        String urlStr = BASE_URL_TAG + videoTag.getAid();
        String response = Global.httpGet(urlStr);

        //ret.data[] ->
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                //ret.data[i] ->
                JsonObject tagObject = jsonArray.get(i).getAsJsonObject();
                int tag_id = tagObject.get("tag_id").getAsInt(); //273675
                String tag_name = tagObject.get("tag_name").getAsString(); //"一拳超人"

                //ret.data[i].count
                tagObject = tagObject.get("count").getAsJsonObject();
                int use = tagObject.get("use").getAsInt(); //19376
                int atten = tagObject.get("atten").getAsInt(); //40785

                output.print(former);
                output.print("," + tag_id);
                output.print("," + tag_name);
                output.print("," + use);
                output.print("," + atten);
            } catch (Exception e) {
                e.printStackTrace();
            }
            output.println();
        }//End of For in "data[]"
    }

}
