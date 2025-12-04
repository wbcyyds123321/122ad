package com.example.a122ad.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 笔记实体类，用于存储笔记的基本信息
 */
public class Note implements Serializable {
    private long id;         // 笔记ID
    private String title;    // 笔记标题
    private String content;  // 笔记内容
    private Date createdAt;  // 创建时间
    private Date updatedAt;  // 更新时间
    private String category; // 笔记分类
    private String imagePath; // 图片路径

    // 构造函数
    public Note() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // 带参构造函数
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.category = "默认分类";
    }

    // Getters and Setters
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
        this.updatedAt = new Date(); // 更新时间戳
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = new Date(); // 更新时间戳
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = new Date(); // 更新时间戳
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.updatedAt = new Date(); // 更新时间戳
    }
}