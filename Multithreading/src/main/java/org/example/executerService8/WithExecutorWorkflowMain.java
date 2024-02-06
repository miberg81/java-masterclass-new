package org.example.executerService8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Set up thread with executer, but in a custom way(allows to add thread name)
 * Factory is needed as a seconf param for the executor
 */
class ColorThreadWorkflowFactory implements ThreadFactory {

    private String threadName;

    public ColorThreadWorkflowFactory(ThreadColor color) {
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

