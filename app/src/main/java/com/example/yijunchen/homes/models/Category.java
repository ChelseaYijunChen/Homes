package com.example.yijunchen.homes.models;

/**
 * Created by yijunchen on 7/3/17.
 */

public class Category {

    private int id;
    private String name;
    private String desc;
    private String status;

    public Category() {
    }

    public Category(int id, String name, String desc, String status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
