package com.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;  
import javax.persistence.Table;

@Entity
@Table(name="keyword")
public class Keyword {
	/**
	 * 关键字的主键
	 */
	private long id;
	/**
     * 关键字的名称
     */
    private String name;
    /**
     * 被引用的次数
     */
    private int times;
    /**
     * 关键字的全拼
     */
    private String nameFullPy;
    /**
     * 关键字的简拼
     */
    private String nameShortPy;
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
    public int getTimes() {
        return times;
    }
    public void setTimes(int times) {
        this.times = times;
    }
    @Column(name="name_full_py")
    public String getNameFullPy() {
        return nameFullPy;
    }
    public void setNameFullPy(String nameFullPy) {
        this.nameFullPy = nameFullPy;
    }
    @Column(name="name_short_py")
    public String getNameShortPy() {
        return nameShortPy;
    }
    public void setNameShortPy(String nameShortPy) {
        this.nameShortPy = nameShortPy;
    }
	

}
