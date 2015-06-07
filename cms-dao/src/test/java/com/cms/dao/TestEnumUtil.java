package com.cms.dao;

import org.junit.Test;

import com.cms.entity.ChannelType;
import com.cms.utils.EnumUtil;

public class TestEnumUtil {
    @Test
    public void testProp2name(){
       ChannelType[] channelType=ChannelType.values();
       for(ChannelType ct:channelType){
           System.out.println(ct.name()+ct.getName());
       }
       // EnumUtil.enumProp2Name(ChannelType.class, "name");
   }
}
