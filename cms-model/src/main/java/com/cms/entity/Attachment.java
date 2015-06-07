package com.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {
    /**
     * 附件的主键
     */
    private long id;
    /**
     * 附件新名字
     */
    private String newName;
    /**
     * 附近旧名字
     */
    private String oldName;
    /**
     * 附件大小
     */
    private Integer size;
    /**
     * 是否在主页显示
     */
    private Boolean isIndexPic;
    /**
     * 该附件是否是图片类型,0表示不是，1表示是
     */
    private int isImg;
    /**
     * 属于那篇文章
     */
    private Topic topic;
    /**
     * 附件的类型，这个类型和contentType类型一致
     */
    private String type;
    /**
     * 附件的后缀名
     */
    private String suffix;
    /**
     * 是否是附件信息，0表示不是，1表示是，如果是附件信息就在文章的附件栏进行显示
     */
    private int isAttach;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "newName")
    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Column(name = "oldName")
    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getIsIndexPic() {
        return isIndexPic;
    }

    public void setIsIndexPic(Boolean isIndexPic) {
        this.isIndexPic = isIndexPic;
    }

    public int getIsImg() {
        return isImg;
    }

    public void setIsImg(int isImg) {
        this.isImg = isImg;
    }

    @ManyToOne()
    @JoinColumn(name = "topicId")
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getIsAttach() {
        return isAttach;
    }

    public void setIsAttach(int isAttach) {
        this.isAttach = isAttach;
    }

    public Attachment() {
    }

    public Attachment(String newName, String oldName, Integer size,
                      Boolean isIndexPic, int isImg, Topic topic, String type,
                      String suffix, int isAttach) {
        this.newName = newName;
        this.oldName = oldName;
        this.size = size;
        this.isIndexPic = isIndexPic;
        this.isImg = isImg;
        this.topic = topic;
        this.type = type;
        this.suffix = suffix;
        this.isAttach = isAttach;
    }


}
