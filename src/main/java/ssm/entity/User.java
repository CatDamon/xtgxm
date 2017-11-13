package ssm.entity;

import java.io.Serializable;

/**
 * Created by dxgong on 2017/6/5.
 */


public class User implements Serializable{
    private String userid;
    private String username;
    private String pre_logintime;
    private String password;
    private String ipadress;
    private Integer available;

    public static final String SELECTUSERALL = "selectUserAll";


    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPre_logintime() {
        return pre_logintime;
    }

    public String getPassword() {
        return password;
    }

    public String getIpadress() {
        return ipadress;
    }

    public int getAvailable() {
        return available;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPre_logintime(String pre_logintime) {
        this.pre_logintime = pre_logintime;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", pre_logintime='" + pre_logintime + '\'' +
                ", password='" + password + '\'' +
                ", ipadress='" + ipadress + '\'' +
                ", available=" + available +
                '}';
    }
}
