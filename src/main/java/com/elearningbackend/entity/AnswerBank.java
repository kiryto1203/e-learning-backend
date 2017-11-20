package com.elearningbackend.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "answer_bank", catalog = "e_learning")
public class AnswerBank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String answerCode;
	private String answerContent;
	private Timestamp creationDate;
	private Timestamp lastUpdateDate;
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

	@Column(name = "creation_date")
	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "last_update_date")
	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
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
