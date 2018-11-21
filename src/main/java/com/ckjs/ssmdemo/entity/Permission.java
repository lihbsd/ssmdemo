package com.ckjs.ssmdemo.entity;

import java.util.Date;

public class Permission {

    private Integer id;
    private String item;
    private String itemname;
    private String permission;
    private String permissionname;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
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
                ",item=" + item +
                ",itemname=" + itemname +
                ",permission=" + permission +
                ",permissionname=" + permissionname +
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