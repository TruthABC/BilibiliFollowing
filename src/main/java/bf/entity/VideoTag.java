package bf.entity;

public class VideoTag {

    //Author Info
    private int mid; //777536
    private String author; //"LexBurner"

    //Video Info
    private int aid; //49025020
    private String title; //"这动画也太沙雕了吧！2019四月新番吐槽大盘点（第二弹）"

    //http://api.bilibili.com/x/tag/archive/tags?aid=49025020
    //ret.data[i]
    private int tag_id; //273675
    private String tag_name; //"一拳超人"

    //ret.data[i].count
    private int use; //19376
    private int atten; //40785

    //ret.data[i]
    //private String content;

    public VideoTag() {}

    public VideoTag(int mid, String author, int aid, String title) {
        this.mid = mid;
        this.author = author;
        this.aid = aid;
        this.title = title;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getAtten() {
        return atten;
    }

    public void setAtten(int atten) {
        this.atten = atten;
    }
}
