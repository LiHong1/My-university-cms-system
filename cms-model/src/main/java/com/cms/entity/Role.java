package com.cms.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 角色对象，用来对应可以访问的功能，系统中为了简单值定义了管理员，发布人员和审核人员
 * @author Administrator
 *
 */
@Entity
@Table
public class Role {
	/**
	 * 角色id
	 */
	private long id;
	/**
	 * 角色的名称，中文
	 */
	private String name;
	/**
	 * 角色的编号，枚举类型
	 */
	private RoleType roleType;  
	/**
	 * 用户
	 */
	private Set<User> users=new HashSet<User>();
	
	 @ManyToMany()
	 @JoinTable(
	             name="users_roles",
	             joinColumns=@JoinColumn(name="roleId"),
	             inverseJoinColumns=@JoinColumn(name="userId")
	            )
	public Set<User> getUsers() {
        return users;
    }



    public void setUsers(Set<User> users) {
        this.users = users;
    }



    public Role() {
	}
	
	
	
	



	public Role(long id, String name, RoleType roleType, Set<User> users) {
        super();
        this.id = id;
        this.name = name;
        this.roleType = roleType;
        this.users = users;
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
	
	@Enumerated(EnumType.STRING)
	@Column(name="role_type")
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	
}
