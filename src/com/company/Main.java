package com.company;

import java.util.*;

public class Main {
    static Map<String, Publisher> pubMap;
    static Map<String, Subscriber> subMap;
    static EventChannel eventChannel;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // 채널 및 pub sub 생성
        eventChannel = new EventChannel();
        pubMap = new HashMap<>();
        subMap = new HashMap<>();

        Publisher snape = new Publisher("Snape");
        Publisher mcgonagall = new Publisher("Mcgonagall");
        Publisher hagrid = new Publisher("Hagrid");
        Subscriber harry = new Subscriber("Harry");
        Subscriber hermione = new Subscriber("Hermione");
        Subscriber ron = new Subscriber("Ron");

        // 출력, 중복 체크용 map
        pubMap.put(snape.getTopic(), snape);
        pubMap.put(mcgonagall.getTopic(), mcgonagall);
        pubMap.put(hagrid.getTopic(), hagrid);
        subMap.put(harry.getName(), harry);
        subMap.put(hermione.getName(), hermione);
        subMap.put(ron.getName(), ron);

        // publish
        snape.publish(new Message(snape.getTopic(), "Potions"), eventChannel);
        mcgonagall.publish(new Message(mcgonagall.getTopic(), "Transfiguration"), eventChannel);
        hagrid.publish(new Message(hagrid.getTopic(), "Care of Magical Creatures"), eventChannel);

        // subscribe
        harry.addSubscriber(mcgonagall.getTopic(), eventChannel);
        hermione.addSubscriber(snape.getTopic(), eventChannel);
        hermione.addSubscriber(mcgonagall.getTopic(), eventChannel);
        hermione.addSubscriber(hagrid.getTopic(), eventChannel);
        ron.addSubscriber(hagrid.getTopic(), eventChannel);

        // get all & print
        eventChannel.getAllMessages();
        printAll();
        System.out.println();

        int type;
        do {
            System.out.print("Input your type(1: Publisher | 2: Subscriber | -1: exit): ");
            type = input.nextInt();
            if (type == 1) {
                pubRequest();
            } else if (type == 2) {
                subRequest();
            } else if (type == -1) {
                System.out.println("exit...\n");
            } else {
                System.out.println("Type does not exist");
            }
        }while(type != -1);

//        Publisher newPub = new Publisher("newPub");
//        pubMap.put(newPub.getTopic(), newPub);
//
//        // publish
//        newPub.publish(new Message(newPub.getTopic(), "Can you Subscribe me?"), eventChannel);
//        eventChannel.getAllMessages();
//        printAll();
    }

    public static void printAll() {
        System.out.println("\n\t\t*** Subscribers ***");
        for (Subscriber sub : subMap.values()) {
            sub.printMessage();
        }
        System.out.println("------------------------------------");
        System.out.println("\t\t*** Publishers ***");
        for (String topic : pubMap.keySet()) {
            eventChannel.printMap(topic);
        }
    }

    public static void pubRequest(){
        Scanner input = new Scanner(System.in);

        // publish
        System.out.println("You are PUBLISHER");
        System.out.print("Input your topic: ");
        String topic = input.next();
        input.nextLine();

        Publisher pub;
        if (pubMap.containsKey(topic)) { // 기존 Publisher
            pub = pubMap.get(topic);
        } else {                         // 새로운 사람
            pub = new Publisher(topic);
            pubMap.put(pub.getTopic(), pub);
        }

        System.out.print("Input contents: ");
        String contents = input.nextLine();
        pub.publish(new Message(topic, contents), eventChannel);

        pubMap.put(pub.getTopic(), pub);

        int check;
        System.out.println("\nMENU (1: publish | 2: print my subscribers | 3: print all | 4: check messageQueue | -1: quit)\n");
        do {
            System.out.print("\nwhat do you want? ");
            check = input.nextInt();
            input.nextLine();

            if (check == 1) {
                System.out.print("Input contents: ");
                contents = input.nextLine();
                pub.publish(new Message(topic, contents), eventChannel);
            } // 구독자 출력
            else if (check == 2) {
                eventChannel.printMap(pub.getTopic());
            } // 전체 출력
            else if (check == 3) {
                printAll();
            } // messageQueue 상태
            else if (check == 4) {
                eventChannel.printMessageQueue();
            } // 종료
            else if (check == -1) {
                System.out.println("exit...\n");
            } // 예외
            else {
                System.out.println("Command not found.\n");
            }

        } while (check != -1);
    }

    public static void subRequest(){
        Scanner input = new Scanner(System.in);

        // subscribe
        System.out.println("You are SUBSCRIBER");
        System.out.print("Input your name: ");
        String name = input.next();

        Subscriber sub;
        if (subMap.containsKey(name)) { // 기존 subscriber
            sub = subMap.get(name);
        } else {                         // 새로운 사람
            sub = new Subscriber(name);
            subMap.put(sub.getName(), sub);
        }

        String topic;
        int check;
        System.out.println("\nMENU (1: subscribe | 2: unSubscribe | 3: get messages by topic |" +
                "\n 4: get all messages | 5: print all | 6: check messageQueue | -1: quit)\n");
        do {
            System.out.print("\nwhat do you want? ");
            check = input.nextInt();

            if (check == 1) {
                System.out.print("Input subscribe topic: ");
                topic = input.next();
                sub.addSubscriber(topic, eventChannel);
            } // unsubscribe
            else if (check == 2) {
                System.out.print("Input unSubscribe topic: ");
                topic = input.next();
                sub.unSubscriber(topic, eventChannel);
            } // get messages by topic
            else if (check == 3) {
                System.out.print("Input get topic: ");
                topic = input.next();
                sub.getMessages(topic, eventChannel);
            } // get all messages
            else if (check == 4) {
                eventChannel.getAllMessages();
            } // 전체 출력
            else if (check == 5) {
                printAll();
            } // messageQueue 상태
            else if (check == 6) {
                eventChannel.printMessageQueue();
            } // 종료
            else if (check == -1) {
                System.out.println("exit...\n");
            } // 예외
            else {
                System.out.println("Command not found.\n");
            }

        } while (check != -1);
    }
}