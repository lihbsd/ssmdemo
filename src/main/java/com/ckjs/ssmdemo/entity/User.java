package com.ckjs.ssmdemo.entity;

import java.util.Date;

public class User {

    private Integer id;
    private Integer personid;
    private String personname;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private String securityquestion1;
    private String securityanswer1;
    private String securityquestion2;
    private String securityanswer2;
    private String securityquestion3;
    private String securityanswer3;
    private Integer roleid;
    private String rolename;
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

    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getSecurityquestion1() {
        return securityquestion1;
    }

    public void setSecurityquestion1(String securityquestion1) {
        this.securityquestion1 = securityquestion1;
    }

    public String getSecurityanswer1() {
        return securityanswer1;
    }

    public void setSecurityanswer1(String securityanswer1) {
        this.securityanswer1 = securityanswer1;
    }

    public String getSecurityquestion2() {
        return securityquestion2;
    }

    public void setSecurityquestion2(String securityquestion2) {
        this.securityquestion2 = securityquestion2;
    }

    public String getSecurityanswer2() {
        return securityanswer2;
    }

    public void setSecurityanswer2(String securityanswer2) {
        this.securityanswer2 = securityanswer2;
    }

    public String getSecurityquestion3() {
        return securityquestion3;
    }

    public void setSecurityquestion3(String securityquestion3) {
        this.securityquestion3 = securityquestion3;
    }

    public String getSecurityanswer3() {
        return securityanswer3;
    }

    public void setSecurityanswer3(String securityanswer3) {
        this.securityanswer3 = securityanswer3;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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
                ",personid=" + personid +
                ",personname=" + personname +
                ",username=" + username +
                ",phone=" + phone +
                ",email=" + email +
                ",securityquestion1=" + securityquestion1 +
                ",securityanswer1=" + securityanswer1 +
                ",securityquestion2=" + securityquestion2 +
                ",securityanswer2=" + securityanswer2 +
                ",securityquestion3=" + securityquestion3 +
                ",securityanswer3=" + securityanswer3 +
                ",roleid=" + roleid +
                ",rolename=" + rolename +
                ",status=" + status +
                ",isdelete=" + isdelete +
                ",creator=" + creator +
                ",createtime=" + createtime +
                ",modifier=" + modifier +
                ",modifytime=" + modifytime +
                '}';
    }
}