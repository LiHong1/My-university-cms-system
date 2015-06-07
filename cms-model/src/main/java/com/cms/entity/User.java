package com.cms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
@Entity
@Table(name="user",uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
public class User {
    private long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private List<Role> roles=new ArrayList<Role>();
    private List<Group> groups=new ArrayList<Group>();
    private List<Topic> topics=new ArrayList<Topic>();
    /**
     * 用户的状态：0表示停用，1表示启用
     */
    private int status;
    @ManyToMany()
    @JoinTable(
             name="users_roles",
             joinColumns=@JoinColumn(name="userId"),
             inverseJoinColumns=@JoinColumn(name="roleId")
            )
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    @OneToMany(mappedBy="user")
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @ManyToMany(cascade=CascadeType.REMOVE)
    @JoinTable(
             name="groups_users",
             joinColumns=@JoinColumn(name="userId"),
             inverseJoinColumns=@JoinColumn(name="groupId")
            )
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> list) {
        this.groups = list;
    }
    private Date createDate;
    public User() {
    }
    public User(String username, String password, String nickname,
            String email, String phone, int status, Date createDate) {
        super();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createDate = createDate;
    }
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    @NotNull(message="用户名不能为空")
    public String getUsername() {
        return username;
    }
   
    public void setUsername(String username) {
        this.username = username;
    }
    //@NotNull(message="密码不能为空")
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Email(message="邮件格式不正确")
    public String getEmail() {
        return email;
    }
   
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    @Column(name="create_date")
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
