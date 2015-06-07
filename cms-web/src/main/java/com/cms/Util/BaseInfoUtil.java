package com.cms.Util;

import com.cms.beans.BaseInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by li hong on 2015/4/18.
 */
public class BaseInfoUtil {
    private static Properties property;
    private static BaseInfoUtil baseInfoUtil;
    private BaseInfoUtil() throws IOException {
        if(property==null){
            property=new Properties();
            property.load(BaseInfoUtil.class.getClassLoader().getResourceAsStream("baseInfo.properties"));
        }
    }
    public static BaseInfoUtil getInstance(){
        if(baseInfoUtil==null)
            try {
                baseInfoUtil=new BaseInfoUtil();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return baseInfoUtil;
    }
    public BaseInfo getBaseInfo(){
        BaseInfo baseInfo=new BaseInfo();
        baseInfo.setAddress(property.getProperty("address"));
        baseInfo.setDomainName(property.getProperty("domainName"));
        baseInfo.setEmail(property.getProperty("email"));
        baseInfo.setIndexPicNumber(Integer.parseInt(property.getProperty("indexPicNumber")));
        baseInfo.setAddress(property.getProperty("address"));
        baseInfo.setName(property.getProperty("name"));
        baseInfo.setPhone(property.getProperty("phone"));
        baseInfo.setRecordCode(property.getProperty("recordCode"));
        baseInfo.setZipCode(property.getProperty("zipCode"));
        String w = property.getProperty("indexPicSize");
        String[] ws = w.split("\\*");
        baseInfo.setIndexPicHeight(Integer.parseInt(ws[1]));
        baseInfo.setIndexPicWidth(Integer.parseInt(ws[0]));
        return baseInfo;
    }
    public BaseInfo setBaseInfo(BaseInfo baseInfo){
        FileOutputStream fileOutputStream=null;
        property.setProperty("address",baseInfo.getAddress());
        property.setProperty("domainName",baseInfo.getDomainName());
        property.setProperty("email",baseInfo.getEmail());
        property.setProperty("name",baseInfo.getName());
        property.setProperty("phone",baseInfo.getPhone());
        property.setProperty("recordCode",baseInfo.getRecordCode());
        property.setProperty("zipCode",baseInfo.getZipCode());
        property.setProperty("indexPicNumber",String.valueOf(baseInfo.getIndexPicNumber()));
        property.setProperty("indexPicNumber",String.valueOf(baseInfo.getIndexPicNumber()));
        property.setProperty("indexPicSize", baseInfo.getIndexPicWidth() + "*" + baseInfo.getIndexPicHeight());
        String path=BaseInfoUtil.class.getClassLoader().getResource("baseInfo.properties").getPath();
        try {
            fileOutputStream=new FileOutputStream(path);
            try {
                property.store(fileOutputStream,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baseInfo;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
                try {
                    if(fileOutputStream!=null)
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
}
