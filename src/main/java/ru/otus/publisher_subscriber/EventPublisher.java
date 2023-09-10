package ru.otus.publisher_subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.LinkedBlockingQueue;

public class EventPublisher {

    Map<EventType, List<EventListener>> subscribers = new ConcurrentHashMap<>();

    LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    public void publish(Event event) {
        eventQueue.add(event);
    }

    public void notifySubscibers(Event event) {
        if (subscribers.containsKey(event.getType())) {
            subscribers.get(event.getType()).forEach(subscriber -> subscriber.accept(event));
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        notifySubscibers(eventQueue.take());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (true);
            }
        }).start();
    }

    public void subscribe(EventType type, EventListener listener) {
        subscribers.computeIfAbsent(type, key -> new ArrayList<>()).add(listener);
    }


}
