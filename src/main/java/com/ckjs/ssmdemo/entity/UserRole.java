package com.ckjs.ssmdemo.entity;

import java.util.Date;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/9 14:14
 */
public class UserRole {
    private Integer id;
    private Integer userid;
    private Integer roleid;
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

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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
        return "Person{" +
                "id=" + id +
                ",userid=" + userid +
                ",roleid=" + roleid +
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