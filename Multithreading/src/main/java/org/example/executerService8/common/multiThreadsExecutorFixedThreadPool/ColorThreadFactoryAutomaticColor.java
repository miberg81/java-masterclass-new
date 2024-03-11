package org.example.executerService8.common.multiThreadsExecutorFixedThreadPool;

import org.example.executerService8.common.ThreadColor;

import java.util.concurrent.ThreadFactory;

public class ColorThreadFactoryAutomaticColor implements ThreadFactory {

    private String threadName;

    // set default color to black , we don't want to use Ansi default color at 0 position
    private int colorValue = 1;

    public ColorThreadFactoryAutomaticColor(ThreadColor color) {
        this.threadName = color.name();
    }

    public ColorThreadFactoryAutomaticColor() {
    }

    /*
       Factory method to create a thread in a custom way.
       By getting next color from enum each time,
       cycling through all none default ansi colors in the enum (all except first)
       colorValue field is reused by this factory for all threads, using the factory method.
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);

        String name = threadName;

        // if no color passed, use black color (position 1 in enum)
        if (name == null) { // name only is null when no args constructor is used;
            name = ThreadColor.values()[colorValue].name();
        }

        // increment colorValue by 1 (move to next enum value),
        // except when end of enum reached (then reset enum to black at position 1)
        // (color value is incremented to next color BEFORE if statement executed)
        if(++colorValue> (ThreadColor.values().length-1)) {
            colorValue = 1;
        }

        thread.setName(name);
        return thread;
    }
}
