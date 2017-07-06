package com.example.yijunchen.homes.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yijunchen on 7/2/17.
 */

public class Property implements Serializable, Parcelable{
    private int id;
    private String name;
    private String description;
    private String type;
    private int category;
    private String address1;
    private String address2;
    private int zipCode;
    private float latitude;
    private float longitude;
    private String imgThumb1;
    private String imgThumb2;
    private String imgThumb3;
    private double cost;
    private int size;
    private String status;
    private String update;
    private int sellerId;

    public Property() {
    }

    public Property(int id, String name, String description, String type, int category, String address1, String address2, int zipCode, float latitude, float longitude, String imgThumb1, String imgThumb2, String imgThumb3, double cost, int size, String status, String update, int sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.category = category;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgThumb1 = imgThumb1;
        this.imgThumb2 = imgThumb2;
        this.imgThumb3 = imgThumb3;
        this.cost = cost;
        this.size = size;
        this.status = status;
        this.update = update;
        this.sellerId = sellerId;
    }

    protected Property(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        type = in.readString();
        category = in.readInt();
        address1 = in.readString();
        address2 = in.readString();
        zipCode = in.readInt();
        latitude = in.readFloat();
        longitude = in.readFloat();
        imgThumb1 = in.readString();
        imgThumb2 = in.readString();
        imgThumb3 = in.readString();
        cost = in.readDouble();
        size = in.readInt();
        status = in.readString();
        update = in.readString();
        sellerId = in.readInt();
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getImgThumb1() {
        return imgThumb1;
    }

    public void setImgThumb1(String imgThumb1) {
        this.imgThumb1 = imgThumb1;
    }

    public String getImgThumb2() {
        return imgThumb2;
    }

    public void setImgThumb2(String imgThumb2) {
        this.imgThumb2 = imgThumb2;
    }

    public String getImgThumb3() {
        return imgThumb3;
    }

    public void setImgThumb3(String imgThumb3) {
        this.imgThumb3 = imgThumb3;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", category=" + category +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", zipCode=" + zipCode +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imgThumb1='" + imgThumb1 + '\'' +
                ", imgThumb2='" + imgThumb2 + '\'' +
                ", imgThumb3='" + imgThumb3 + '\'' +
                ", cost=" + cost +
                ", size=" + size +
                ", status='" + status + '\'' +
                ", update='" + update + '\'' +
                ", sellerId=" + sellerId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeInt(category);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeInt(zipCode);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeString(imgThumb1);
        dest.writeString(imgThumb2);
        dest.writeString(imgThumb3);
        dest.writeDouble(cost);
        dest.writeInt(size);
        dest.writeString(status);
        dest.writeString(update);
        dest.writeInt(sellerId);
    }
}
