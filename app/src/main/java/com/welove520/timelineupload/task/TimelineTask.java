package com.welove520.timelineupload.task;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Raomengyang on 18-8-3.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
@Entity(indexes = {@Index(value = "id", unique = true)})
public class TimelineTask implements Serializable {

    private static final long serialVersionUID = -8386311363172667778L;

    @Id
    private Long id;
    private String name;
    private Long sendTimeMS;
    private Integer progress;
    private boolean flag;

    @Generated(hash = 1792820822)
    public TimelineTask(Long id, String name, Long sendTimeMS, Integer progress,
            boolean flag) {
        this.id = id;
        this.name = name;
        this.sendTimeMS = sendTimeMS;
        this.progress = progress;
        this.flag = flag;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public boolean getFlag() {
        return this.flag;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
