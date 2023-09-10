package ru.otus.publisher_subscriber;

import ru.otus.publisher_subscriber.listeners.DeleteListener;
import ru.otus.publisher_subscriber.listeners.InsertListener;

public class Test {
    public static void main(String[] args) throws Exception{
        EventPublisher publisher = new EventPublisher();
        publisher.start();

        EventedList list = new EventedList();
        list.setPublisher(publisher);

        publisher.subscribe(EventType.ADD, new InsertListener());
        publisher.subscribe(EventType.DELETE, new DeleteListener());

        list.add("1234");
        list.add("5678");
        list.remove(0);

        System.out.println("Done");




        Thread.sleep(100000);

    }
}
