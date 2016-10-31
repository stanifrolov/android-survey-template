package com.example.stanislavfrolov.quizapp;

class Answer {

    private String timestamp;
    private String question;
    private String answer;

    Answer() {
        this.timestamp = "";
        this.question = "";
        this.answer = "";
    }

    String getTimestamp() {
        return timestamp;
    }

    void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    String getAnswer() {
        return answer;
    }

    void setAnswer(String answer) {
        this.answer = answer;
    }

    String getQuestion() {
        return question;
    }

    void setQuestion(String question) {
        this.question = question;
    }
}
