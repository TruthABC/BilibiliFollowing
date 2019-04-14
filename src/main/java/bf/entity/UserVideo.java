package bf.entity;

public class UserVideo {

    //http://space.bilibili.com/ajax/member/getSubmitVideos?page=1&pagesize=50&mid=777536
    //ret.data.vlist[i] ->
    private int mid; //777536
    private String author; //"LexBurner"

    private int aid; //49025020
    private String title; //"这动画也太沙雕了吧！2019四月新番吐槽大盘点（第二弹）"
    private long created; //1555003187
    private String length; // "09:44" // -> 584
    private String pic; //"//i2.hdslb.com/bfs/archive/5313a7244976f744f69e8041a2242dde12c872ba.jpg"

    //private int play; //2310044
    //private int favorites; //111586
    //private int comment; //6197

    /////////////////////////////// EXTENDED ///////////////////////////////
    //http://api.bilibili.com/x/web-interface/view?aid=49025020
    //ret.data ->
    //private int aid; //49025020
    //private long ctime; //1555003187
    private int duration; // 584 // -> "09:44"

    private int tid; //27
    private String tname; // "综合"

    //ret.data.stat ->
    //private int aid; //49025020
    private int view; //2316083 //播放
    private int danmaku; //12387 //弹幕
    private int reply; //6210 //评论

    private int like; //251635 //点赞 （素质三连）
    private int coin; //305624 //投币
    private int favorite; //111792 //收藏

    public UserVideo() {}
    public UserVideo(int mid, String author) {
        this.mid = mid;
        this.author = author;
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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(int danmaku) {
        this.danmaku = danmaku;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
