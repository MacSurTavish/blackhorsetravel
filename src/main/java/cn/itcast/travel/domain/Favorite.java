package cn.itcast.travel.domain;

import java.io.Serializable;

/**
 * 收藏实体类
 */
public class Favorite implements Serializable {
    private int rid;//旅游线路对象
    private String date;//收藏时间
    private User user;//所属用户

    /**
     * 无参构造方法
     */
    public Favorite() {
    }

    /**
     * 有参构造方法
     * @param rid
     * @param date
     * @param user
     */
    public Favorite(int rid, String date, User user) {
            this.rid = rid;
            this.date = date;
            this.user = user;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
