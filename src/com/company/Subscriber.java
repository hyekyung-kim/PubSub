package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Subscriber {
    String name;
    Set<Message> subMessages = new HashSet<>();

    Subscriber(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<Message> getSubMessages() {
        return subMessages;
    }
    public void setSubMessages(Set<Message> subMessages) {
        this.subMessages = subMessages;
    }


    public void addSubscriber(String topic, EventChannel eventChannel){
        eventChannel.addSubscriber(topic, this);
    }

    public void removeSubscriber(String topic, EventChannel eventChannel){
        eventChannel.removeSubscriber(topic, this);
    }

    public void getMessages(String topic, EventChannel eventChannel){
        eventChannel.getMessages(topic, this);
    }

    public void printMessage(){
        System.out.println(name);

        for(Message m : subMessages){
            System.out.println("- " + m.getTopic() + ": " + m.getContents());
        }
    }
}
