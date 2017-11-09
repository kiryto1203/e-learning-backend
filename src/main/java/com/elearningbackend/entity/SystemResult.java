package com.elearningbackend.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "system_result", catalog = "e_learning")
public class SystemResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer systemResultId;
	private QuestionBank questionBank;
	private String systemResultCorrectId;
	private String systemResultIncorrectId;
	private Date creationDate;
	private Date lastUpdateDate;
	private String creatorUsername;
	private String lastUpdaterUsername;

	public SystemResult() {
	}

	public SystemResult(QuestionBank questionBank, String creatorUsername) {
		this.questionBank = questionBank;
		this.creatorUsername = creatorUsername;
	}

	public SystemResult(QuestionBank questionBank, String systemResultCorrectId, String systemResultIncorrectId,
			Date creationDate, Date lastUpdateDate, String creatorUsername, String lastUpdaterUsername) {
		this.questionBank = questionBank;
		this.systemResultCorrectId = systemResultCorrectId;
		this.systemResultIncorrectId = systemResultIncorrectId;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.creatorUsername = creatorUsername;
		this.lastUpdaterUsername = lastUpdaterUsername;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "system_result_id", unique = true, nullable = false)
	public Integer getSystemResultId() {
		return this.systemResultId;
	}

	public void setSystemResultId(Integer systemResultId) {
		this.systemResultId = systemResultId;
	}



	@Column(name = "system_result_correct_id", length = 100)
	public String getSystemResultCorrectId() {
		return this.systemResultCorrectId;
	}

	public void setSystemResultCorrectId(String systemResultCorrectId) {
		this.systemResultCorrectId = systemResultCorrectId;
	}

	@Column(name = "system_result_incorrect_id", length = 100)
	public String getSystemResultIncorrectId() {
		return this.systemResultIncorrectId;
	}

	public void setSystemResultIncorrectId(String systemResultIncorrectId) {
		this.systemResultIncorrectId = systemResultIncorrectId;
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "system_result_question_code", nullable = false)
	public QuestionBank getQuestionBank() {
		return this.questionBank;
	}

	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}
}
