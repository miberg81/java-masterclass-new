package org.example.executerService8.common.singleThreadExecutor;

import org.example.executerService8.common.ThreadColor;

import java.util.concurrent.ThreadFactory;

public class ColorThreadFactoryCustomColor implements ThreadFactory {

    private String threadName;

    public ColorThreadFactoryCustomColor(ThreadColor color) {
        this.threadName = color.name();
    }

    /*
    Factory method to create a thread in a custom way.
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
