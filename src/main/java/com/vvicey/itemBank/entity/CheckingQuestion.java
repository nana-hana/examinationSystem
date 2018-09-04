package com.vvicey.itemBank.entity;

/**
 * @Author nana
 * @Date 18-7-9 上午9:19
 * @Description 判断题实体
 */
public class CheckingQuestion {

    private int id;//id
    private Integer examEiid;//对应创建考试
    private String question;//问题
    private String answerA;//答案A
    private String answerB;//答案B
    private String trueAnswer;//正确答案
    private Integer level;//该题难度
    private String studentAnswer;//学生选择的答案

    
    public Integer getExamEiid() {
		return examEiid;
	}

	public void setExamEiid(Integer examEiid) {
		this.examEiid = examEiid;
	}

	public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA == null ? null : answerA.trim();
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB == null ? null : answerB.trim();
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer == null ? null : trueAnswer.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}