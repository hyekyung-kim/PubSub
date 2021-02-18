package com.company;

public class Publisher {
    String topic;

    Publisher(String topic){
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }

    void publish(Message message, EventChannel eventChannel){
        eventChannel.addMessage(message);
    }
}
