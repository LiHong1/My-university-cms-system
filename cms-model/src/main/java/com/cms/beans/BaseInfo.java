package com.cms.beans;

/**
 * Created by li hong on 2015/4/18.
 * 网站基本信息类
 */
public class BaseInfo {
    private String name;
    private String address;
    private String zipCode;
    private String recordCode;
    private String phone;
    private String email;
    private String domainName;
    private int indexPicWidth;
    private int indexPicHeight;
    private int indexPicNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getIndexPicWidth() {
        return this.indexPicWidth;
    }

    public void setIndexPicWidth(int indexPicWidth) {
        this.indexPicWidth = indexPicWidth;
    }

    public int getIndexPicHeight() {
        return this.indexPicHeight;
    }

    public void setIndexPicHeight(int indexPicHeight) {
        this.indexPicHeight = indexPicHeight;
    }

    public int getIndexPicNumber() {
        return this.indexPicNumber;
    }

    public void setIndexPicNumber(int indexPicNumber) {
        this.indexPicNumber = indexPicNumber;
    }
}
