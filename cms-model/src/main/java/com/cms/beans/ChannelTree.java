package com.cms.beans;

import java.math.BigInteger;


public class ChannelTree {
        private String name;
        private BigInteger id;
        public BigInteger getId() {
            return id;
        }
        public void setId(BigInteger id) {
            this.id = id;
        }
        private BigInteger pid;
        public BigInteger getPid() {
            return pid;
        }
        public void setPid(BigInteger pid) {
            this.pid = pid;
        }
        public void setPid(Long pid) {
            this.pid = BigInteger.valueOf(pid);
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

 
        public ChannelTree(BigInteger id,String name, BigInteger pid) {
            super();
            this.name = name;
            this.id = id;
            this.pid = pid;
        }
        public ChannelTree(Long id,String name, Long pid) {
            super();
            this.name = name;
            this.id = BigInteger.valueOf(id);
            this.pid = BigInteger.valueOf(pid);
        }
        public ChannelTree(){
            
        }
}
