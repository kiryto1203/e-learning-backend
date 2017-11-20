package com.elearningbackend.entity;

import javax.persistence.*;

@Entity
@Table(name = "question_lession", catalog = "e_learning")
public class QuestionLession implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private QuestionLessionId id;
	private Lession lession;
	private QuestionBank questionBank;
	private String userAnswer;
	private Float point;

	public QuestionLession() {
	}

	public QuestionLession(QuestionLessionId id, Lession lession, QuestionBank questionBank) {
		this.id = id;
		this.lession = lession;
		this.questionBank = questionBank;
	}

	public QuestionLession(QuestionLessionId id, Lession lession, QuestionBank questionBank, String userAnswer,
			Float point) {
		this.id = id;
		this.lession = lession;
		this.questionBank = questionBank;
		this.userAnswer = userAnswer;
		this.point = point;
	}

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "questionLessionLessionCode", column = @Column(name = "question_lession_lession_code", nullable = false, length = 100)),
		@AttributeOverride(name = "questionLessionQuestionCode", column = @Column(name = "question_lession_question_code", nullable = false, length = 100)) })
	public QuestionLessionId getId() {
		return this.id;
	}

	public void setId(QuestionLessionId id) {
		this.id = id;
	}

	@Column(name = "user_answer", length = 65535)
	public String getUserAnswer() {
		return this.userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	@Column(name = "point", precision = 6)
	public Float getPoint() {
		return this.point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	@ManyToOne
	@JoinColumn(name = "question_lession_lession_code", nullable = false, insertable = false, updatable = false)
	public Lession getLession() {
		return this.lession;
	}

	public void setLession(Lession lession) {
		this.lession = lession;
	}

	@ManyToOne
	@JoinColumn(name = "question_lession_question_code", nullable = false, insertable = false, updatable = false)
	public QuestionBank getQuestionBank() {
		return this.questionBank;
	}

	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}
}
