package com.elearningbackend.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "question_bank", catalog = "e_learning")
public class QuestionBank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String questionCode;
	private int questionType;
	private String questionContent;
	private String questionParentCode;
	private Date creationDate;
	private Date lastUpdateDate;
	private String creatorUsername;
	private String lastUpdaterUsername;
	private SystemResult systemResult;
	private Set<QuestionLession> questionLessions = new HashSet<QuestionLession>(0);

	public QuestionBank() {
	}

	public QuestionBank(String questionCode) {
            this.questionCode = questionCode;
	}

	@Id
	@Column(name = "question_code", unique = true, nullable = false, length = 100)
	public String getQuestionCode() {
		return this.questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	@Column(name = "question_type")
	public int getQuestionType() {
            return this.questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	@Column(name = "question_content", length = 65535)
	public String getQuestionContent() {
		return this.questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	@Column(name = "question_parent_code", length = 100)
	public String getQuestionParentCode() {
		return this.questionParentCode;
	}

	public void setQuestionParentCode(String questionParentCode) {
		this.questionParentCode = questionParentCode;
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

	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "question_code")
	public SystemResult getSystemResult() {
		return this.systemResult;
	}

	public void setSystemResult(SystemResult systemResult) {
		this.systemResult = systemResult;
	}

	@OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL)
	public Set<QuestionLession> getQuestionLessions() {
		return this.questionLessions;
	}

	public void setQuestionLessions(Set<QuestionLession> questionLessions) {
		this.questionLessions = questionLessions;
	}

}
