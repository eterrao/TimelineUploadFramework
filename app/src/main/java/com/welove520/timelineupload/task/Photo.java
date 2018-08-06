package com.welove520.timelineupload.task;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Raomengyang on 18-8-6.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
@Entity(indexes = {@Index(value = "photoId DESC")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 6210733500432515515L;

    private Long id;
    @Id
    private Long photoId;
    private Long sendTimeMS;
    private String name;
    private String path;
    private boolean isUploaded;

    @Generated(hash = 777630900)
    public Photo(Long id, Long photoId, Long sendTimeMS, String name, String path,
            boolean isUploaded) {
        this.id = id;
        this.photoId = photoId;
        this.sendTimeMS = sendTimeMS;
        this.name = name;
        this.path = path;
        this.isUploaded = isUploaded;
    }

    @Generated(hash = 1043664727)
    public Photo() {
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsUploaded() {
        return this.isUploaded;
    }

    public void setIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public Long getSendTimeMS() {
        return this.sendTimeMS;
    }

    public void setSendTimeMS(Long sendTimeMS) {
        this.sendTimeMS = sendTimeMS;
    }
}
