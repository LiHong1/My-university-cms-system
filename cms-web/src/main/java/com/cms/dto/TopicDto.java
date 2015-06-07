package com.cms.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.cms.entity.Channel;
import com.cms.entity.Topic;


public class TopicDto {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	private int id;
	private String title;
	/**
	 * 关键字:通过|来分割不同的关键字
	 */
	private String keyword;
	/**
	 * 文章的状态，默认为0表示未发表，1表示已发布
	 */
	private int status;
	/**
	 * 是否是推荐文章,0表示不推荐，1表示推荐
	 */
	private int recommend;
	/**
	 * 文章的内容
	 */
	private String content;
	/**
	 * 文章的摘要
	 */
	private String summary;
	/**
	 * 栏目图片id，如果该栏目是图片类型的栏目，就会显示这个id的图片
	 */
	private int channelPicId;
	/**
	 * 文章的发布时间，用来进行排序的
	 */
	private String publishDate;
	/**
	 * 文章的栏目id
	 */
	private Long cid;
	/**
	 * 栏目名称
	 */
	private String cname;
	/**
	 * 附件id
	 */
    private Long attachIds[];
	//关键字
	private String aks[];

	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

	public String[] getAks() {
        return aks;
    }
    public void setAks(String[] aks) {
        this.aks = aks;
    }
    public Long[] getAttachIds() {
        return attachIds;
    }
    public void setAttachIds(Long[] attachIds) {
        this.attachIds = attachIds;
    }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotEmpty(message="文章标题不能为空")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public int getChannelPicId() {
		return channelPicId;
	}
	public void setChannelPicId(int channelPicId) {
		this.channelPicId = channelPicId;
	}
	
	public String getPublishDate() {
		return publishDate;
	}
	
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	@Min(value=1,message="必须选择一个栏目id")
	public Long getCid() {
		return cid;
	}
	public void setCid(Long id) {
		this.cid = id;
	}
	
	public TopicDto() {
	}
	
	public Topic getTopic(Topic t) {
		if(t==null)
			t=new Topic();
		t.setChannelPicId(this.getChannelPicId());
		t.setContent(this.getContent());
		t.setId(this.getId());
		try {
			Date d = sdf.parse(this.getPublishDate());
			Calendar cd = Calendar.getInstance();
			cd.setTime(d);
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.set(cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DATE));
			t.setPublishDate(ca.getTime());
		} catch (ParseException e) {
			t.setPublishDate(new Date());
		}
		t.setRecommend(this.getRecommend());
		t.setStatus(this.getStatus());
		t.setSummary(this.getSummary());
		t.setTitle(this.getTitle());
		if(aks!=null) {
		    StringBuffer bf=new StringBuffer();
            for(String k:aks) {
                bf.append(k).append("|");
            }
            this.setKeyword(bf.toString());
        }
		t.setKeyword(this.getKeyword());
		System.out.println(t.getKeyword());
		return t;
	}
	
	public TopicDto(Topic topic) {
		this.setChannelPicId(topic.getChannelPicId());
		this.setContent(topic.getContent());
		this.setId((int) topic.getId());
		this.setKeyword(topic.getKeyword());
		this.setPublishDate(sdf.format(topic.getPublishDate()));
		this.setRecommend(topic.getRecommend());
		this.setStatus(topic.getStatus());
		this.setSummary(topic.getSummary());
		this.setTitle(topic.getTitle());
		this.setCname(topic.getCname());
	}
	
	public TopicDto(Topic topic,Long cid) {
		this.setChannelPicId(topic.getChannelPicId());
		this.setContent(topic.getContent());
		this.setId((int) topic.getId());
		this.setCid(cid);
		this.setKeyword(topic.getKeyword());
		this.setPublishDate(sdf.format(topic.getPublishDate()));
		this.setRecommend(topic.getRecommend());
		this.setStatus(topic.getStatus());
		this.setSummary(topic.getSummary());
		this.setTitle(topic.getTitle());
	}
    public String[] getKeywords() {
        String keywords=this.getKeyword();
		System.out.println(keywords);
		if(keywords!=null)
        this.setAks(keywords.split("\\|"));
		return this.getAks();
    }
    public void setChannel(Channel channel) {
        this.setCid(channel.getId());
        this.setCname(channel.getName());
    }
	
}
