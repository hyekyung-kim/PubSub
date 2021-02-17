package com.company;

public class Message {
    private String topic;
    private String contents;

    public Message(String topic, String contents){
        this.topic = topic;
        this.contents = contents;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }
    public String getTopic(){
        return topic;
    }

    public void setContents(String contents){
        this.contents = contents;
    }
    public String getContents(){
        return contents;
    }
}
