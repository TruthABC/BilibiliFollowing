package bf.nt;

import bf.Global;
import bf.entity.DetailedUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.PrintStream;
import java.util.*;

public class UserDetailCrawler {

    public static final String BASE_URL1 = "http://api.bilibili.com/x/space/acc/info?mid=";
    public static final String BASE_URL2 = "http://api.bilibili.com/x/relation/stat?vmid=";
    public static final String BASE_URL3 = "http://space.bilibili.com/ajax/member/getTags?mids=";

    private Set<String> mTargetNames;
    private Map<Integer, String> mMid2Name;
    private Map<Integer, DetailedUser> mUsers;

    public UserDetailCrawler () {}

    public void initializeTargetNamesFromCSVV(String path) {
        Scanner scanner = Global.getFileScanner(path);
        if (scanner == null) {
            return;
        }

        mTargetNames = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            mTargetNames.add(line);
        }
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
            if (mTargetNames.contains(parts[1])) {
                mMid2Name.put(Integer.parseInt(parts[0]), parts[1]);
            }
        }
    }

    public void crawlAndFillDetails() {
        mUsers = new HashMap<>();
        for (int mid : mMid2Name.keySet()) {
            String name = mMid2Name.get(mid);
            crawl(mid, name);
        }
    }

    private void crawl(int mid, String name) {
        System.out.println("[ " + mUsers.size() + " ] mid: " + mid + "; name: " + name);

        DetailedUser user = new DetailedUser();
        user.setMid(mid);
        user.setName(name);

        String response;
        JsonParser parser = new JsonParser();
        JsonObject jsonObject;

//        private String sex; //"男"
//        private String face; //"http://i1.hdslb.com/bfs/face/2996e22a24eed2d7767e452627a9130207defe6a.jpg"
        response = Global.httpGet(BASE_URL1 + mid);
        jsonObject = parser.parse(response).getAsJsonObject();
        jsonObject = jsonObject.get("data").getAsJsonObject();
        String sex = jsonObject.get("sex").getAsString();
        String face = jsonObject.get("face").getAsString();
        user.setSex(sex);
        user.setFace(face);

//        private int following; //21
//        private int follower; //4943441
        response = Global.httpGet(BASE_URL2 + mid);
        jsonObject = parser.parse(response).getAsJsonObject();
        jsonObject = jsonObject.get("data").getAsJsonObject();
        int following = jsonObject.get("following").getAsInt();
        int follower = jsonObject.get("follower").getAsInt();
        user.setFollowing(following);
        user.setFollower(follower);

//        private String[] tags;
        response = Global.httpGet(BASE_URL3 + mid);
        jsonObject = parser.parse(response).getAsJsonObject();
        jsonObject = jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("tags").getAsJsonArray();
        String[] tags = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            tags[i] = jsonArray.get(i).getAsString();
        }
        user.setTags(tags);

        mUsers.put(mid, user);
    }


    public void saveDetailsToCSVV(String filePath) {
        if (mUsers == null) {
            return;
        }

        PrintStream output = Global.getFilePrintStream(filePath);

        if (output==null) {
            return;
        }

        output.println("mid,name,sex,face,following,follower");

        for (DetailedUser u: mUsers.values()) {
            output.print(u.getMid() + ",");
            output.print(u.getName() + ",");
            output.print(u.getSex() + ",");
            output.print(u.getFace() + ",");
            output.print(u.getFollowing() + ",");
            output.print(u.getFollower());
            output.println();
        }

        output.flush();
        output.close();
    }

    public void saveTagsToCSVV(String filePath) {
        if (mUsers == null) {
            return;
        }

        PrintStream output = Global.getFilePrintStream(filePath);

        if (output==null) {
            return;
        }

        output.println("mid,name,tags");

        for (DetailedUser u: mUsers.values()) {
            output.print(u.getMid() + ",");
            output.print(u.getName());
            for (String tag: u.getTags()) {
                output.print("," + tag);
            }
            output.println();
        }

        output.flush();
        output.close();
    }
}
