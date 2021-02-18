package com.company;

import java.util.*;

public class EventChannel {
    private final Queue<Message> messageQueue = new LinkedList<>();
    private final Map<String, Set<Subscriber>> pubsubMap = new HashMap<>();
    private final Set<String> pubTopics = new HashSet<>();

    // pub: message 추가
    public void addMessage(Message message){
        messageQueue.add(message);
        pubTopics.add(message.getTopic());
    }

    // sub: sub 추가
    public void addSubscriber(String topic, Subscriber sub){
        Set<Subscriber> subscribers;

        if(pubsubMap.containsKey(topic)){  // 존재하는 topic
            subscribers = pubsubMap.get(topic);
            subscribers.add(sub);
            pubsubMap.put(topic, subscribers);
        }else {                            // 새로운 topic
            subscribers = new HashSet<>();
            if(pubTopics.contains(topic)){ // 구독 요청한 topic 존재
                subscribers.add(sub);
                pubsubMap.put(topic, subscribers);
            }else{                         // 존재하지 않는 topic
                System.out.println("Topic doesn't exist");
            }
        }
    }

    // sub: sub 삭제
    public void unSubscriber(String topic, Subscriber sub){
        // topic을 키로 갖는 set에서 sub제거
        if(pubsubMap.containsKey(topic)){
            Set<Subscriber> subscribers = pubsubMap.get(topic);
            subscribers.remove(sub);
            pubsubMap.put(topic, subscribers);
        }else{
            System.out.println("You are NOT subscriber");
        }
    }

    // sub: sub이 요청한 topic message 가져오기
    public void getMessages(String topic, Subscriber sub){

        for(Message sendMessage: messageQueue) {
            // topic, sub이 일치하는 Message
            if (sendMessage.getTopic().equals(topic)) {
                System.out.println("Message exist: " + sub.getName() + "'s request [ " + topic + " ]");
                Set<Subscriber> students = pubsubMap.get(topic);

                for (Subscriber student : students) {
                    // 구독중이 아니면 보내지 않음
                    if (student.equals(sub)) {
                        Set<Message> messages = student.getSubMessages();
                        messages.add(sendMessage);
                        student.setSubMessages(messages);
                    }
                }
            }
        }
    }

    // 모든 sub message 가져오기
    public void getAllMessages(){
        System.out.println("Get all messages...");
        if(messageQueue.isEmpty()){
            System.out.println("Message Queue is Empty\n");
        }else{
            while(!messageQueue.isEmpty()) {
                Message sendMessage = messageQueue.poll();
                String professor = sendMessage.getTopic();

                Set<Subscriber> students = pubsubMap.get(professor);

                if(students == null) {
                    System.out.println("No subscribers\n");
                    break;
                }
                for(Subscriber student : students){
                    Set<Message> messages = student.getSubMessages();
                    messages.add(sendMessage);
                    student.setSubMessages(messages);
                }

            }
        }
    }

    // 구독 현황
    public void printMap(String topic){
        System.out.print(topic + ": ");
        Set<Subscriber> tmp = pubsubMap.get(topic);

        if(tmp == null || tmp.isEmpty()) {
            System.out.println("!!! No Subscribers !!!");
        }else{
            for(Subscriber s: tmp){
                System.out.print(s.name + " / ");
            }
            System.out.println();
        }
    }

    public void printMessageQueue(){
        System.out.println("Queue Status");
        for(Message m : messageQueue){
            System.out.println(m.getTopic() + ": " + m.getContents());
        }
    }

}
