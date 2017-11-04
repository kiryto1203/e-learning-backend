package com.elearningbackend.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lession", catalog = "e_learning")
public class Lession implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String lessionCode;
	private User user;
	private Date creationDate;
	private Date lastUpdateDate;
	private Set<QuestionLession> questionLessions = new HashSet<QuestionLession>(0);

	public Lession() {
	}

	public Lession(String lessionCode, User user) {
		this.lessionCode = lessionCode;
		this.user = user;
	}

	public Lession(String lessionCode, User user, Date creationDate, Date lastUpdateDate,
			Set<QuestionLession> questionLessions) {
		this.lessionCode = lessionCode;
		this.user = user;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.questionLessions = questionLessions;
	}

	@Id
	@Column(name = "lession_code", unique = true, nullable = false, length = 100)
	public String getLessionCode() {
		return this.lessionCode;
	}

	public void setLessionCode(String lessionCode) {
		this.lessionCode = lessionCode;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_date", length = 19)
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@OneToMany(mappedBy = "lession", cascade= CascadeType.ALL)
	public Set<QuestionLession> getQuestionLessions() {
		return this.questionLessions;
	}

	public void setQuestionLessions(Set<QuestionLession> questionLessions) {
		this.questionLessions = questionLessions;
	}

	@ManyToOne
	@JoinColumn(name = "lession_username", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
