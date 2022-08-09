package com.muedsa.github.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class GithubReleaseTagInfo {
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "mage:alt")
    private String imageAlt;
    @JSONField(name = "image:width")
    private Integer imageWidth;
    @JSONField(name = "image:height")
    private Integer imageHeight;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "url")
    private String url;
    @JSONField(name = "tag")
    private String tag;
    @JSONField(name = "description")
    private String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
