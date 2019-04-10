package bf.entity;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String face;
    private List<User> followings;

    public User() {}

    public User(String uid, String name, String face, List<User> followings) {
        this.uid = uid;
        this.name = name;
        this.face = face;
        this.followings = followings;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    /******************************************************************************************************************/
    @Override
    public String toString() {
        return "{" + uid + ", " + name + "}";
    }
}
