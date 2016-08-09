package com.google.lenono.common;

/**
 * Created by lenono on 2016-08-01.
 */
public class User_info {
    private String id;//电话作为idkey值
    private String nickname;
    private String phone_num;
    private String address1;
    private String address1_detail;
    private String address2;
    private String address2_detail;
    private String address3;
    private String address3_detail;
    private String address4;
    private String address4_detail;
    private String address5;
    private String address5_detail;
    private String first_shop;//是否是首单
    private String tickets;//优惠内容

    public User_info() {
    }

    public User_info(String id, String nickname, String phone_num, String address1, String address1_detail, String address2, String address2_detail, String address3, String address3_detail, String address4, String address4_detail, String address5, String address5_detail, String first_shop, String tickets) {
        this.id = id;
        this.nickname = nickname;
        this.phone_num = phone_num;
        this.address1 = address1;
        this.address1_detail = address1_detail;
        this.address2 = address2;
        this.address2_detail = address2_detail;
        this.address3 = address3;
        this.address3_detail = address3_detail;
        this.address4 = address4;
        this.address4_detail = address4_detail;
        this.address5 = address5;
        this.address5_detail = address5_detail;
        this.first_shop = first_shop;
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "User_info{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", address1='" + address1 + '\'' +
                ", address1_detail='" + address1_detail + '\'' +
                ", address2='" + address2 + '\'' +
                ", address2_detail='" + address2_detail + '\'' +
                ", address3='" + address3 + '\'' +
                ", address3_detail='" + address3_detail + '\'' +
                ", address4='" + address4 + '\'' +
                ", address4_detail='" + address4_detail + '\'' +
                ", address5='" + address5 + '\'' +
                ", address5_detail='" + address5_detail + '\'' +
                ", first_shop='" + first_shop + '\'' +
                ", tickets='" + tickets + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1_detail() {
        return address1_detail;
    }

    public void setAddress1_detail(String address1_detail) {
        this.address1_detail = address1_detail;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2_detail() {
        return address2_detail;
    }

    public void setAddress2_detail(String address2_detail) {
        this.address2_detail = address2_detail;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress3_detail() {
        return address3_detail;
    }

    public void setAddress3_detail(String address3_detail) {
        this.address3_detail = address3_detail;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress4_detail() {
        return address4_detail;
    }

    public void setAddress4_detail(String address4_detail) {
        this.address4_detail = address4_detail;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress5_detail() {
        return address5_detail;
    }

    public void setAddress5_detail(String address5_detail) {
        this.address5_detail = address5_detail;
    }

    public String getFirst_shop() {
        return first_shop;
    }

    public void setFirst_shop(String first_shop) {
        this.first_shop = first_shop;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }
}
