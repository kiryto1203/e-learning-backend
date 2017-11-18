package com.elearningbackend.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answer_bank", catalog = "e_learning")
public class AnswerBank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String answerCode;
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

	@Id
	@Column(name = "answer_id", unique = true, nullable = false, length = 100)
	public String getAnswerCode() {
		return this.answerCode;
	}

	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
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
