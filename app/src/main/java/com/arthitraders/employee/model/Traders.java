package com.arthitraders.employee.model;

public class Traders {
    private String traders_name;
    private int traders_img;

    public Traders(String traders_name, int traders_img) {
        this.traders_name = traders_name;
        this.traders_img = traders_img;
    }

    public String getTraders_name() {
        return traders_name;
    }

    public int getTraders_img() {
        return traders_img;
    }

    public void setTraders_name(String traders_name) {
        this.traders_name = traders_name;
    }

    public void setTraders_img(int traders_img) {
        this.traders_img = traders_img;
    }
}
