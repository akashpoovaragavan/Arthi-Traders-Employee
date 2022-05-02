package com.arthitraders.employee.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String userName;
    private String userId;
    private String usercheck;


    public UserModel(String userName, String userid, String usercheck) {
        this.userName = userName;
        this.userId = userid;
        this.usercheck = usercheck;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsercheck() {
        return usercheck;
    }

    public void setUsercheck(String usercheck) {
        this.usercheck = usercheck;
    }
}
