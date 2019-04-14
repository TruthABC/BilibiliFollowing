package bf.nt;

import bf.Global;
import bf.entity.User;
import bf.entity.UserVideo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserVideosCrawler {

    //http://space.bilibili.com/ajax/member/getSubmitVideos?pagesize=50&mid=777536&page=1
    //http://api.bilibili.com/x/web-interface/view?aid=49025020
    public static final String BASE_URL1 = "http://space.bilibili.com/ajax/member/getSubmitVideos?pagesize=50&mid=";
    public static final String PAGE_PARAM1 = "&page=";
    public static final String BASE_URL2 = "http://api.bilibili.com/x/web-interface/view?aid=";

    private Map<Integer, String> mMid2Name;
    private Map<Integer, UserVideo> mUserVideos;

    public UserVideosCrawler() {
    }


    public void initializeSimpleMappingFromCSVV(String path) {
        Scanner scanner = Global.getFileScanner(path);
        if (scanner == null) {
            return;
        }

        mMid2Name = new HashMap<>();

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            mMid2Name.put(Integer.parseInt(parts[0]), parts[1]);
        }
    }

    public void crawlBasicVideoInfoWhileSavingCSV(String filePath) {
        PrintStream output = Global.getFilePrintStream(filePath);
        if (output == null) {
            return;
        }
        output.println("mid,author,aid,title,created,length,pic");

//        boolean flag = false;
        try {
            for (int mid : mMid2Name.keySet()) {
//                if (mid == 50329118) {
//                    flag = true;
//                }
//                if (!flag) {
//                    continue;
//                }
                mUserVideos = new HashMap<>();
                String name = mMid2Name.get(mid);
                crawl(mid, name);
                for (UserVideo uv : mUserVideos.values()) {
                    output.print(uv.getMid() + ",");
                    output.print(uv.getAuthor() + ",");
                    output.print(uv.getAid() + ",");
                    output.print(uv.getTitle() + ",");
                    output.print(uv.getCreated() + ",");
                    output.print(uv.getLength() + ",");
                    output.print(uv.getPic());
                    output.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        output.flush();
        output.close();
    }

    private void crawl(int mid, String name) {
        System.out.println("[ " + mUserVideos.size() + " ] mid: " + mid + "; name: " + name);

        int pages = 1;
        for (int pageNow = 1; pageNow <= pages; pageNow++) {
            String urlStr = BASE_URL1 + mid + PAGE_PARAM1 + pageNow;

            // HTTP GET
            String response = Global.httpGet(urlStr);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response).getAsJsonObject();
            jsonObject = jsonObject.get("data").getAsJsonObject();

            //ret.data ->
            int count = jsonObject.get("count").getAsInt();
//            int pages = jsonObject.get("pages").getAsInt();
//            videoNum = count;
            pages = jsonObject.get("pages").getAsInt();

            //ret.data.vlist[] ->
            JsonArray jsonArray = jsonObject.get("vlist").getAsJsonArray();
            if (jsonArray.size() == 0) {
                break;
            }

            //Get every video basic infomation
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonVideo = jsonArray.get(i).getAsJsonObject();
                UserVideo userVideo = new UserVideo(mid, name);

                //get data
                int aid = jsonVideo.get("aid").getAsInt(); //49025020
                String title = jsonVideo.get("title").getAsString(); //"这动画也太沙雕了吧！2019四月新番吐槽大盘点（第二弹）"
                long created = jsonVideo.get("created").getAsLong(); //1555003187
                String length = jsonVideo.get("length").getAsString(); // "09:44" // -> 584
                String pic = jsonVideo.get("pic").getAsString(); //"//i2.hdslb.com/bfs/archive/5313a7244976f744f69e8041a2242dde12c872ba.jpg"

                //fill list item
                userVideo.setAid(aid);
                userVideo.setTitle(title);
                userVideo.setCreated(created);
                userVideo.setLength(length);
                userVideo.setPic(pic);

                //add to list (set)
                mUserVideos.put(aid, userVideo);
            }
        }//END for (int pageNow = 1; pageNow <= pages; pageNow++)
    }

}