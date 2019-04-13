package bf.entity;

public class DetailedUser {

    //http://api.bilibili.com/x/space/acc/info?mid=777536
    //ret.data ->
    private int mid; //777536
    private String name; //"LexBurner"
    private String sex; //"男"
    private String face; //"http://i1.hdslb.com/bfs/face/2996e22a24eed2d7767e452627a9130207defe6a.jpg"

    //http://api.bilibili.com/x/relation/stat?vmid=777536
    //ret.data ->
    //private int mid;
    private int following; //21
    private int follower; //4943441

    //http://space.bilibili.com/ajax/member/getTags?mids=777536
    //ret.data ->
    //private int mid;
    private String[] tags;

    ///////////////////////// EXTENDED /////////////////////////
    //private int submitVideo; //274

    ///////////////////////// EXTENDED2 /////////////////////////
    //private double submitHour; //???

    ///////////////////////// EXTENDED3 /////////////////////////
    //private double submitFrequency; //???

    public DetailedUser() {}

    public DetailedUser(int mid) {
        this.mid = mid;
    }

    public DetailedUser(int mid, String name, String sex, String face, int following, int follower, String[] tags) {
        this.mid = mid;
        this.name = name;
        this.sex = sex;
        this.face = face;
        this.following = following;
        this.follower = follower;
        this.tags = tags;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

}
