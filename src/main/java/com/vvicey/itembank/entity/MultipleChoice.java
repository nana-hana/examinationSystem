package com.vvicey.itembank.entity;

/**
 * @Author nana
 * @Date 18-7-9 上午11:20
 * @Description 多选题实体类
 */
public class MultipleChoice {

    /**
     * id
     */
    private int id;
    /**
     * 科目
     */
    private Integer examEiid;
    /**
     * 问题
     */
    private String question;
    /**
     * 答案A
     */
    private String answerA;
    /**
     * 答案B
     */
    private String answerB;
    /**
     * 答案C
     */
    private String answerC;
    /**
     * 答案D
     */
    private String answerD;
    /**
     * 正确答案
     */
    private String trueAnswer;
    /**
     * 该题难度
     */
    private Integer level;
    /**
     * 学生选择的答案
     */
    private String studentAnswer;

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

    public Integer getExamEiid() {
		return examEiid;
	}

	public void setExamEiid(Integer examEiid) {
		this.examEiid = examEiid;
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

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC == null ? null : answerC.trim();
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD == null ? null : answerD.trim();
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