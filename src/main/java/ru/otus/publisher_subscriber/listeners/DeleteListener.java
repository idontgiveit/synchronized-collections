package ru.otus.publisher_subscriber.listeners;

import ru.otus.publisher_subscriber.Event;
import ru.otus.publisher_subscriber.EventListener;

public class DeleteListener implements EventListener {
    @Override
    public void accept(Event event) {
        Sleeper.sleep(2000);
        System.out.println("Deleted " + event.getObject());
    }
}
