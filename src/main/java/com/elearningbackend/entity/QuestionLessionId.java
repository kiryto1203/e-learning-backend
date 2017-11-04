package com.elearningbackend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuestionLessionId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String questionLessionLessionCode;
	private String questionLessionQuestionCode;

	public QuestionLessionId() {
	}

	public QuestionLessionId(String questionLessionLessionCode, String questionLessionQuestionCode) {
		this.questionLessionLessionCode = questionLessionLessionCode;
		this.questionLessionQuestionCode = questionLessionQuestionCode;
	}

	@Column(name = "question_lession_lession_code", nullable = false, length = 100)
	public String getQuestionLessionLessionCode() {
		return this.questionLessionLessionCode;
	}

	public void setQuestionLessionLessionCode(String questionLessionLessionCode) {
		this.questionLessionLessionCode = questionLessionLessionCode;
	}

	@Column(name = "question_lession_question_code", nullable = false, length = 100)
	public String getQuestionLessionQuestionCode() {
		return this.questionLessionQuestionCode;
	}

	public void setQuestionLessionQuestionCode(String questionLessionQuestionCode) {
		this.questionLessionQuestionCode = questionLessionQuestionCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof QuestionLessionId))
			return false;
		QuestionLessionId castOther = (QuestionLessionId) other;

		return ((this.getQuestionLessionLessionCode() == castOther.getQuestionLessionLessionCode())
				|| (this.getQuestionLessionLessionCode() != null && castOther.getQuestionLessionLessionCode() != null
						&& this.getQuestionLessionLessionCode().equals(castOther.getQuestionLessionLessionCode())))
				&& ((this.getQuestionLessionQuestionCode() == castOther.getQuestionLessionQuestionCode())
						|| (this.getQuestionLessionQuestionCode() != null
								&& castOther.getQuestionLessionQuestionCode() != null
								&& this.getQuestionLessionQuestionCode()
										.equals(castOther.getQuestionLessionQuestionCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getQuestionLessionLessionCode() == null ? 0 : this.getQuestionLessionLessionCode().hashCode());
		result = 37 * result
				+ (getQuestionLessionQuestionCode() == null ? 0 : this.getQuestionLessionQuestionCode().hashCode());
		return result;
	}

}
