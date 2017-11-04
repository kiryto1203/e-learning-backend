package com.elearningbackend.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "answer_bank", catalog = "e_learning")
public class AnswerBank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer answerId;
	private String answerContent;
	private Date creationDate;
	private Date lastUpdateDate;
	private String creatorUsername;
	private String lastUpdaterUsername;

	public AnswerBank() {
	}

	public AnswerBank(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	public AnswerBank(String answerContent, Date creationDate, Date lastUpdateDate, String creatorUsername,
			String lastUpdaterUsername) {
		this.answerContent = answerContent;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.creatorUsername = creatorUsername;
		this.lastUpdaterUsername = lastUpdaterUsername;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "answer_id", unique = true, nullable = false)
	public Integer getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	@Column(name = "answer_content", length = 65535)
	public String getAnswerContent() {
		return this.answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
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

	@Column(name = "creator_username", nullable = false)
	public String getCreatorUsername() {
		return this.creatorUsername;
	}

	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	@Column(name = "last_updater_username")
	public String getLastUpdaterUsername() {
		return this.lastUpdaterUsername;
	}

	public void setLastUpdaterUsername(String lastUpdaterUsername) {
		this.lastUpdaterUsername = lastUpdaterUsername;
	}

}
