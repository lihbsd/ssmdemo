package com.ckjs.ssmdemo.entity;

import java.util.Date;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/2 11:51
 */
public class Region {

    private Integer id;
    private String code;
    private String name;
    private String level;
    private Integer superid;
    private String supername;
    private Boolean startup;
    private String remark;
    private Integer status;
    private Boolean isdelete;
    private Integer creator;
    private Date createtime;
    private Integer modifier;
    private Date modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getSuperid() {
        return superid;
    }

    public void setSuperid(Integer superid) {
        this.superid = superid;
    }

    public String getSupername() {
        return supername;
    }

    public void setSupername(String supername) {
        this.supername = supername;
    }


    public Boolean getStartup() {
        return startup;
    }

    public void setStartup(Boolean startup) {
        this.startup = startup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }


    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ",code=" + code +
                ",name=" + name +
                ",level=" + level +
                ",superid=" + superid +
                ",supername=" + supername +
                ",startup=" + startup +
                ",remark=" + remark +
                ",status=" + status +
                ",isdelete=" + isdelete +
                ",creator=" + creator +
                ",createtime=" + createtime +
                ",modifier=" + modifier +
                ",modifytime=" + modifytime +
                '}';
    }
}