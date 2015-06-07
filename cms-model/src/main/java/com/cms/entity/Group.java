package com.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group {
    /**
    * 组id
    */
   private long id;
   /**
    * 组名称
    */
   private String name;
   /**
    * 组描述信息
    */
   private String descr;
   
   private Set<User> users=new HashSet<User>();
   
   private Set<Channel> channels=new HashSet<Channel>();
   @ManyToMany
   @JoinTable(
           name="channels_groups",
           joinColumns=@JoinColumn(name="groupId"),
           inverseJoinColumns=@JoinColumn(name="channelId")
           )
   public Set<Channel> getChannels() {
    return channels;
   }
   public void setChannels(Set<Channel> channels) {
     this.channels = channels;
   }
   @ManyToMany(mappedBy="groups",cascade=CascadeType.REMOVE)
   public Set<User> getUsers() {
    return users;
   }
   public void setUsers(Set<User> users) {
       this.users = users;
   }

   public Group(long id, String name, String descr, Set<User> users) {
    super();
    this.name = name;
    this.descr = descr;
   // this.users = users;
}
public Group() {
}
   @Id
   @GeneratedValue
   public long getId() {
       return id;
   }
   public void setId(long id) {
       this.id = id;
   }
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public String getDescr() {
       return descr;
   }
   public void setDescr(String descr) {
       this.descr = descr;
   }
   
}
