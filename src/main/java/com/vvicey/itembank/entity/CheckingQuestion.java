package com.vvicey.itembank.entity;

/**
 * @Author nana
 * @Date 18-7-9 上午9:19
 * @Description 判断题实体
 */
public class CheckingQuestion {
    /**
     * id
     */
    private int id;
    /**
     * 对应创建考试
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
     * 正确答案
     */
    private String trueAnswer;
    /**
     * 该题难度
     */
    private Integer level;
    /**
     * 学生选择的答案管理员的登陆id
     */
    private String studentAnswer;


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