package com.ckjs.ssmdemo.entity;

import java.util.Date;

public class Person {

    private Integer id;
    private Integer profilepictureid;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String wechatnumber;
    private String gender;
    private Date birthdate;
    private Integer regionid;
    private String regionname;
    private String identitytype;
    private String identitynumber;
    private String role;
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

    public Integer getProfilepictureid() {
        return profilepictureid;
    }

    public void setProfilepictureid(Integer profilepictureid) {
        this.profilepictureid = profilepictureid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechatnumber() {
        return wechatnumber;
    }

    public void setWechatnumber(String wechatnumber) {
        this.wechatnumber = wechatnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    public String getIdentitytype() {
        return identitytype;
    }

    public void setIdentitytype(String identitytype) {
        this.identitytype = identitytype;
    }

    public String getIdentitynumber() {
        return identitynumber;
    }

    public void setIdentitynumber(String identitynumber) {
        this.identitynumber = identitynumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                ",profilepictureid=" + profilepictureid +
                ",name=" + name +
                ",nickname=" + nickname +
                ",phone=" + phone +
                ",email=" + email +
                ",wechatnumber=" + wechatnumber +
                ",gender=" + gender +
                ",birthdate=" + birthdate +
                ",regionid=" + regionid +
                ",identitytype=" + identitytype +
                ",identitynumber=" + identitynumber +
                ",role=" + role +
                ",status=" + status +
                ",isdelete=" + isdelete +
                ",creator=" + creator +
                ",createtime=" + createtime +
                ",modifier=" + modifier +
                ",modifytime=" + modifytime +
                '}';
    }
}