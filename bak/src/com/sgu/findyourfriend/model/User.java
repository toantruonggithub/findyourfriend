package com.sgu.findyourfriend.model;

import java.sql.Timestamp;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class User {

	// attributes
	private String name;
	private int gender; // 0: no select, 1: boy, 2: girl
	private String province;
	private String email;
	private String avatar;
	private String gcmId;
	private Timestamp lastestLogin;
	
	private String phoneNumber;
	private boolean isAvailable = true;
	private boolean isAccepted = true;
	private LatLng lastLocation;
	private List<History> steps;

	// methods

	public User(String name, int gender, String province, String email,
			String avatar, String gcmid, Timestamp lastestlogin) {
		this.setName(name);
		this.setGender(gender);
		this.setProvince(province);
		this.setEmail(email);
		this.setAvatar(avatar);
		this.setGcmid(gcmid);
		this.setLastestlogin(lastestlogin);
	}

	public User(String name) {
		this(name, 0, null, null, null, null, new Timestamp(0));
	}

	public User(User user) {
		this(user.name, user.gender, user.province, user.email, user.avatar, user.gcmId, user.lastestLogin);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getGender() {
		return this.gender;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return this.province;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmid(String gcmId) {
		this.gcmId = gcmId;
	}

	public Timestamp getLastestlogin() {
		return lastestLogin;
	}

	public void setLastestlogin(Timestamp lastestLogin) {
		this.lastestLogin = lastestLogin;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public LatLng getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(LatLng lastLocation) {
		this.lastLocation = lastLocation;
	}

	public List<History> getSteps() {
		return steps;
	}

	public void setSteps(List<History> steps) {
		this.steps = steps;
	}
}
