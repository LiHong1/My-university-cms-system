package com.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="topic")
public class Topic {

    /**
	 * 文章的主键
	 */
	private long id;
	/**
	 * 文章题目
	 */
	private String title;
	/**
	 * 状态   0未发布   1已发布
	 */
	private int status;
	
	/**
	 * 是否推荐   0不推荐 1推荐
	 */
	private int recommend;
	/**
     * 关键字:通过|来分割不同的关键字
     */
    private String keyword;
    /**
     * 文章的创建时间
     */
    private Date createDate;
    /**
     * 栏目图片id，如果该栏目是图片类型的栏目，就会显示这个id的图片
     */
    private int channelPicId;
	/**
	 * 发布时间
	 */
	private Date publishDate;
	/**
	 * 附件
	 */
	private List<Attachment> attachments;
	/**
	 * 内容
	 */
	private String content;
    /**
     * 摘要
     */
	private String summary;
	/**
     * 文章的作者名称，用来显示用户的昵称，冗余字段
     */
    private String author;
    /**
     * 栏目名称冗余字段
     */
    private String cname;
    
    /**
     * 文章所在的栏目，多对一
     */
    private Channel channel;
    /**
     * 文章的发布者
     */
    private User user;
	
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getChannelPicId() {
        return channelPicId;
    }
    public void setChannelPicId(int channelPicId) {
        this.channelPicId = channelPicId;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    @ManyToOne
    @JoinColumn(name="channelId")
    public Channel getChannel() {
        return channel;
    }
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    @ManyToOne
    @JoinColumn(name="userId")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getRecommend() {
        return recommend;
    }
    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    public Date getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    @OneToMany(mappedBy="topic",cascade=CascadeType.REMOVE)
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    @Column(length=5000)
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
	
}
