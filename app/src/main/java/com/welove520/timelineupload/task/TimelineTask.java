package com.welove520.timelineupload.task;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Raomengyang on 18-8-3.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
@Entity(indexes = {@Index(value = "sendTimeMS DESC", unique = true)})
public class TimelineTask implements Serializable {

    private static final long serialVersionUID = -8386311363172667778L;

    @Id
    private Long id;
    private String name;
    private Long sendTimeMS;
    private Integer progress;
    private Integer uploadStatus;
    private boolean uploaded;

    @ToMany(referencedJoinProperty = "photoId")
    private List<Photo> photoList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1567528594)
    private transient TimelineTaskDao myDao;

    @Generated(hash = 931356604)
    public TimelineTask(Long id, String name, Long sendTimeMS, Integer progress,
                        Integer uploadStatus, boolean uploaded) {
        this.id = id;
        this.name = name;
        this.sendTimeMS = sendTimeMS;
        this.progress = progress;
        this.uploadStatus = uploadStatus;
        this.uploaded = uploaded;
    }

    @Generated(hash = 493500930)
    public TimelineTask() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSendTimeMS() {
        return sendTimeMS;
    }

    public void setSendTimeMS(Long sendTimeMS) {
        this.sendTimeMS = sendTimeMS;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public Integer getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Integer uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public boolean getFlag() {
        return this.uploaded;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getUploaded() {
        return this.uploaded;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1372473765)
    public List<Photo> getPhotoList() {
        if (photoList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoDao targetDao = daoSession.getPhotoDao();
            List<Photo> photoListNew = targetDao._queryTimelineTask_PhotoList(id);
            synchronized (this) {
                if (photoList == null) {
                    photoList = photoListNew;
                }
            }
        }
        return photoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1816394616)
    public synchronized void resetPhotoList() {
        photoList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1291655326)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTimelineTaskDao() : null;
    }

    public static class UploadStatus {
        public static final int COMPLETED = 0;
        public static final int FAILED = 1;
        public static final int UPLOADING = 2;
        public static final int DELETED = 3;
    }
}
