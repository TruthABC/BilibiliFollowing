package bf.nt;

import bf.Global;
import bf.entity.UserVideo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserVideosDetailCrawler {

    //http://api.bilibili.com/x/web-interface/view?aid=49025020
    public static final String BASE_URL_VIEW = "http://api.bilibili.com/x/web-interface/view?aid=";
    public static final String BASE_URL_STAT = "https://api.bilibili.com/x/web-interface/archive/stat?aid=";

    private Map<Integer, UserVideo> mUserVideos;

    public void initializeBasicFromCSVV(String path) {
        Scanner scanner = Global.getFileScanner(path);
        if (scanner == null) {
            return;
        }

        mUserVideos = new HashMap<>();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            int aid = Integer.parseInt(parts[2]);
            UserVideo uv = new UserVideo(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    aid,
                    parts[3],
                    Long.parseLong(parts[4]),
                    parts[5],
                    parts[6]);
            mUserVideos.put(aid, uv);
        }
    }

    public void crawlDetailedVideoInfoWhileSavingCSV(String filePath) {
        PrintStream output = Global.getFilePrintStream(filePath);
        if (output == null) {
            return;
        }
        output.print("mid,author,aid,title,created,length,pic");
        output.print(",duration,tid,tname");
        output.print(",view,danmaku,reply,like,coin,favorite");
        output.println();

//        boolean flag = false;
        try {
            for (int aid : mUserVideos.keySet()) {
//                if (aid == 13255673) { //日语吹替“乌鸦坐飞机”！山下配音阿福的全部技能，想不想学？
//                    flag = true;
//                }
//                if (!flag) {
//                    continue;
//                }
                UserVideo uv = mUserVideos.get(aid);
                crawlWhilePrintWithView(uv, output);
                //crawlWhilePrintWithStat(uv, output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        output.flush();
        output.close();
    }

    private int mCounter = 0;
//    private void crawlWhilePrintWithStat(UserVideo userVideoBasic, PrintStream output) {
//        System.out.println("[ " + mCounter++ + " ] aid: " + userVideoBasic.getAid() + "; title: " + userVideoBasic.getTitle());
//
//        //Print them directly - 1: basic
//        output.print(userVideoBasic.getMid());
//        output.print("," + userVideoBasic.getAuthor());
//        output.print("," + userVideoBasic.getAid());
//        output.print("," + userVideoBasic.getTitle());
//        output.print("," + userVideoBasic.getCreated());
//        output.print("," + userVideoBasic.getLength());
//        output.print("," + userVideoBasic.getPic());
//
//        // HTTP GET
//        String urlStr = BASE_URL_STAT + userVideoBasic.getAid();
//        String response = Global.httpGet(urlStr);
//
//        //ret.data ->
//        JsonParser parser = new JsonParser();
//        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
//        try {
//            jsonObject = jsonObject.get("data").getAsJsonObject();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            output.println();
//            return;
//        }
//
//        try {
//            //ret.data ->
//            int view = jsonObject.get("view").getAsInt(); //2316083 //播放
//            int danmaku = jsonObject.get("danmaku").getAsInt(); //12387 //弹幕
//            int reply = jsonObject.get("reply").getAsInt(); //6210 //评论
//            int like = jsonObject.get("like").getAsInt(); //251635 //点赞 （素质三连）
//            int coin = jsonObject.get("coin").getAsInt(); //305624 //投币
//            int favorite = jsonObject.get("favorite").getAsInt(); //111792 //收藏
//
//            //Print them directly - 3: statistics
//            output.print("," + view);
//            output.print("," + danmaku);
//            output.print("," + reply);
//            output.print("," + like);
//            output.print("," + coin);
//            output.print("," + favorite);
//        } catch (Exception e) {
//            e.printStackTrace();
//            output.println();
//            return;
//        }
//        output.println();
//    }

    private void crawlWhilePrintWithView(UserVideo userVideoBasic, PrintStream output) throws InterruptedException {
        System.out.println("[ " + mCounter++ + " ] aid: " + userVideoBasic.getAid() + "; title: " + userVideoBasic.getTitle());
        if (mCounter % 40 == 0) {
            Thread.sleep(1*60*1000);
        }

        //Print them directly - 1: basic
        output.print(userVideoBasic.getMid());
        output.print("," + userVideoBasic.getAuthor());
        output.print("," + userVideoBasic.getAid());
        output.print("," + userVideoBasic.getTitle());
        output.print("," + userVideoBasic.getCreated());
        output.print("," + userVideoBasic.getLength());
        output.print("," + userVideoBasic.getPic());

        // HTTP GET
        String urlStr = BASE_URL_VIEW + userVideoBasic.getAid();
        String response = Global.httpGet(urlStr);

        //ret.data ->
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        try {
            jsonObject = jsonObject.get("data").getAsJsonObject();
        } catch (NullPointerException e) {
            e.printStackTrace();
            output.println();
            return;
        }

        //ret.data -> {*}
        int duration = jsonObject.get("duration").getAsInt(); // 584 // -> "09:44"
        int tid = jsonObject.get("tid").getAsInt(); //27
        String tname = jsonObject.get("tname").getAsString(); // "综合"

        //ret.data.stat ->
        jsonObject = jsonObject.get("stat").getAsJsonObject();
        int view = jsonObject.get("view").getAsInt(); //2316083 //播放
        int danmaku = jsonObject.get("danmaku").getAsInt(); //12387 //弹幕
        int reply = jsonObject.get("reply").getAsInt(); //6210 //评论
        int like = jsonObject.get("like").getAsInt(); //251635 //点赞 （素质三连）
        int coin = jsonObject.get("coin").getAsInt(); //305624 //投币
        int favorite = jsonObject.get("favorite").getAsInt(); //111792 //收藏

        //Print them directly - 2: detail
        tname = tname.replace(',','，');
        tname = tname.replace('\n','#');
        output.print("," + duration);
        output.print("," + tid);
        output.print("," + tname);

        //Print them directly - 3: statistics
        output.print("," + view);
        output.print("," + danmaku);
        output.print("," + reply);
        output.print("," + like);
        output.print("," + coin);
        output.print("," + favorite);
        output.println();
    }

}
