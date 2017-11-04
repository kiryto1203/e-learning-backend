package com.elearningbackend.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user", catalog = "e_learning")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String passwordDigest;
	private String activationDigest;
	private Boolean activated;
	private Date activatedAt;
	private String rememberDigest;
	private String resetDigest;
	private Date resetSentAt;
	private Date createdAt;
	private Date updatedAt;
	private String displayName;
	private String email;
	private String phone;
	private String address;
	private String avatar;
	private String role;
	private Set<Lession> lessions = new HashSet<Lession>(0);

	public User() {
	}

	public User(String username, String passwordDigest, Date createdAt, Date updatedAt, String email) {
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.email = email;
	}

	public User(String username, String passwordDigest, String activationDigest, Boolean activated, Date activatedAt,
			String rememberDigest, String resetDigest, Date resetSentAt, Date createdAt, Date updatedAt,
			String displayName, String email, String phone, String address, String avatar, String role,
			Set<Lession> lessions) {
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.activationDigest = activationDigest;
		this.activated = activated;
		this.activatedAt = activatedAt;
		this.rememberDigest = rememberDigest;
		this.resetDigest = resetDigest;
		this.resetSentAt = resetSentAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.displayName = displayName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.role = role;
		this.lessions = lessions;
	}

	@Id
	@Column(name = "username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password_digest", nullable = false, length = 200)
	public String getPasswordDigest() {
		return this.passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	@Column(name = "activation_digest", length = 200)
	public String getActivationDigest() {
		return this.activationDigest;
	}

	public void setActivationDigest(String activationDigest) {
		this.activationDigest = activationDigest;
	}

	@Column(name = "activated")
	public Boolean getActivated() {
		return this.activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activated_at", length = 19)
	public Date getActivatedAt() {
		return this.activatedAt;
	}

	public void setActivatedAt(Date activatedAt) {
		this.activatedAt = activatedAt;
	}

	@Column(name = "remember_digest", length = 200)
	public String getRememberDigest() {
		return this.rememberDigest;
	}

	public void setRememberDigest(String rememberDigest) {
		this.rememberDigest = rememberDigest;
	}

	@Column(name = "reset_digest", length = 200)
	public String getResetDigest() {
		return this.resetDigest;
	}

	public void setResetDigest(String resetDigest) {
		this.resetDigest = resetDigest;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reset_sent_at", length = 19)
	public Date getResetSentAt() {
		return this.resetSentAt;
	}

	public void setResetSentAt(Date resetSentAt) {
		this.resetSentAt = resetSentAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 19)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "display_name", length = 100)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "avatar")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "role", length = 50)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<Lession> getLessions() {
		return this.lessions;
	}

	public void setLessions(Set<Lession> lessions) {
		this.lessions = lessions;
	}
}
