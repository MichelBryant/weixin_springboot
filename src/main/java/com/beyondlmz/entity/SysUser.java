package com.beyondlmz.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysUser {
   private Integer id;
   private String account;//账户
   private String password;
   private String nickname;//呢称
   private String email;//邮件
   private String openid;//微信唯一标识
   private Date createTime;//创建时间
	private Date lastTime;//上次登录时间
	private String usertype;//用户类型 1 普通用户  2 管理员用户
	private Set<SysRole> SysRoles = new HashSet<SysRole>(0);// 所对应的角色集合

	public SysUser() {
	}
	public SysUser(String account, String email, String password, Date lastTime,Date createTime, Set<SysRole> SysRoles) {
		this.account = account;
		this.email = email;
		this.password = password;
		this.createTime = createTime;
		this.lastTime = lastTime;
		this.SysRoles = SysRoles;
	}


	public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getAccount() {
	return account;
}
public void setAccount(String account) {
	this.account = account;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Set<SysRole> getSysRoles() {
		return SysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		SysRoles = sysRoles;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
}
