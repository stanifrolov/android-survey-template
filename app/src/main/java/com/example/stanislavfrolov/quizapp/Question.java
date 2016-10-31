package com.example.stanislavfrolov.quizapp;

class Question {

    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;

    Question() {
        id = 0;
        question = "";
        optionA = "";
        optionB = "";
        optionC = "";
    }

    Question(String question, String optionA, String optionB, String optionC) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
    }

    public int getId() {
        return id;
    }

    String getQuestion() {
        return question;
    }

    String getOptionA() {
        return optionA;
    }

    String getOptionB() {
        return optionB;
    }

    String getOptionC() {
        return optionC;
    }

    public void setId(int id) {
        this.id = id;
    }

    void setQuestion(String question) {
        this.question = question;
    }

    void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    void setOptionC(String optionC) {
        this.optionC = optionC;
    }

}
