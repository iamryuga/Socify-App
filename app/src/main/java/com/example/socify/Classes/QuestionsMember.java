package com.example.socify.Classes;

public class QuestionsMember {

    String username,url,userid,key,question,time,tag, questionURI;

    public String getQuestionURI() {
        return questionURI;
    }

    public void setQuestionURI(String questionURI) {
        this.questionURI = questionURI;
    }

    public QuestionsMember() {

    }

    public String getUsername() {
        return username;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

