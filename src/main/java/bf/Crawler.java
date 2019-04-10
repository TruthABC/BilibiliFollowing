package bf;

import bf.entity.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;

public class Crawler {

    public static final String BASE_URL = "http://api.bilibili.com/x/relation/followings?vmid=";
    public static final String NUM_PARAM = "&ps=";
    public static final String PAGE_PARAM = "&pn=";

    private List<User> mUsers;
    private List<Integer> mUserDepth;
    private Map<String, Integer> mUid2Index;

    public Crawler() {
    }

    public void initializeFirstUser(User firstUser) {
        mUsers = new ArrayList<>();
        mUserDepth = new ArrayList<>();
        mUid2Index = new HashMap<>();
        mUsers.add(firstUser);
        mUserDepth.add(0);
        mUid2Index.put(firstUser.getUid(), 0);
    }

    public void crawlTillDepth(int maxDepth) {
        int nowIndex = 0;
        while (mUsers.size() > nowIndex) {
            if (mUserDepth.get(nowIndex) + 1 > maxDepth) {
                break;
            }
            User user = mUsers.get(nowIndex);
            int depth = mUserDepth.get(nowIndex);
            crawl(user, depth);
            nowIndex++;
        }
    }

    private void crawl(User userNow, int depth) {
        System.out.println("Depth: " + depth + "; UserNow: " + userNow.toString() + "; QueueSize: " + mUsers.size());
        String uid = userNow.getUid();

        //limit: only first 5 pages are accessible
        for (int pageNow = 1; pageNow <= 5; pageNow++) {
            String urlStr = BASE_URL + uid + NUM_PARAM + "50" + PAGE_PARAM + pageNow;

            // HTTP GET
            String response = Global.httpGet(urlStr);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response).getAsJsonObject();
            jsonObject = jsonObject.get("data").getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("list").getAsJsonArray();

            if (jsonArray.size() == 0) {
                break;
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonUser = jsonArray.get(i).getAsJsonObject();
                String newUid = jsonUser.get("mid").getAsString();
                String newName = jsonUser.get("uname").getAsString();
                String newFace = jsonUser.get("face").getAsString();
                int newDepth = depth + 1;

                if (mUid2Index.containsKey(newUid)) {
                    int index = mUid2Index.get(newUid);
                    User oldUser = mUsers.get(index);
                    userNow.getFollowings().add(oldUser);
                } else {
                    int index = mUsers.size();
                    User newUser = new User(newUid , newName, newFace, new ArrayList<>());
                    mUsers.add(newUser);
                    mUserDepth.add(newDepth);
                    mUid2Index.put(newUid, index);
                    userNow.getFollowings().add(newUser);
                    System.out.println("Depth: " + depth + "; NewUser: " + newUser.toString() + "; QueueSize: " + mUsers.size());
                }
            }
        }// pageNow loop
    }

    public void saveToCSVV(String filePath) {
        if (mUsers == null) {
            return;
        }

        PrintStream output = Global.getFilePrintStream(filePath);

        if (output==null) {
            return;
        }

        output.println("UID,Name,Face,Followings");

        for (User u: mUsers) {
            output.print(u.getUid() + ",");
            output.print(u.getName() + ",");
            output.print(u.getFace());
            for (User fu: u.getFollowings()) {
                output.print("," + fu.getUid());
            }
            output.println();
        }

        output.flush();
        output.close();
    }

    public void saveToCSVVEdge(String filePath) {
        if (mUsers == null) {
            return;
        }

        PrintStream output = Global.getFilePrintStream(filePath);

        if (output==null) {
            return;
        }

        output.println("Vertex 1,Vertex 2");

        for (User u: mUsers) {
            for (User fu: u.getFollowings()) {
                output.println(u.getName() + "," + fu.getName());
            }
        }

        output.flush();
        output.close();
    }

    /******************************************************************************************************************/
    public List<User> getmUsers() {
        return mUsers;
    }

    public void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
    }

}
