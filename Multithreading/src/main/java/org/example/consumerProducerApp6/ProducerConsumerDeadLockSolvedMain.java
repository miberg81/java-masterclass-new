package org.example.consumerProducerApp6;

import java.util.Random;

/*
This code solves the dead-locks by adding wait-notify.
 */
public class ProducerConsumerDeadLockSolvedMain {

    public static void main(String[] args) {

        // the resource shared by both threads
        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWriter(messageRepository));

        reader.start();
        writer.start();
    }
}

/*
Shared resource for all threads
 */
class MessageRepository {
    private String message;

    // Indicates whether there is job to do
    // When false = producer can populate the field
    // When the message is true - the consumer can read it.
    private boolean hasMessage = false;

    public synchronized void write(String message) {
        while (hasMessage) {
            // do nothing while already exists message that was not consumed yet
            // exit the loop means message was read(consumed)
            try {
                // release the lock of this thread on the repo for other thread to do smth.
                // that will allow to break out of this loop
                wait();
                // this waiting will finish when this thread will be notified
                // we should not assume the wait ended because the condition of this loop is met
                // because this could be some other unrelated notification
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = true;
        notifyAll(); // wakes up all threads that are waiting on this objects monitor
        // here we write new message
        this.message = message;
    }

    public synchronized String read() {
        //  when consumer calls read, it will wait
        // in the loop until there is a message to read
        while (!hasMessage) {
            // without wait, dead-lock happens, i.e.
            // this thread will continue looping, waiting the other thread to do smth
            // but the other thread is unable to hasMessage to true
            // because it can't obtain the repo! (it's locked by this thread)
            try {
                wait(); // release the lock for other thread to do smth!
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = false; // set to "false" to allow producer write next message
        notifyAll();
        return message;
    }
}

class MessageReader implements Runnable {

    private MessageRepository sharedMessageRepo;

    public MessageReader(MessageRepository sharedMessageRepo) {
        this.sharedMessageRepo = sharedMessageRepo;
    }

    @Override
    public void run() {
        Random random = new Random();
        String latestMessage = "";

        do {
            try {
                // sleep first, so producer will have time to set the message;
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latestMessage = sharedMessageRepo.read();
            System.out.println(latestMessage);

        } while (!latestMessage.equals("Finished"));
    }
}

class MessageWriter implements Runnable {

    private MessageRepository messageRepo;

    private final String text =
            "\nHumpty Dumpty sat on a wall," +
            "\nHumpty Dumpty had a gerat fall,"+
            "\nAll the king's horses and all the king's men,"+
            "\nCouldn't put Humpty together again.";

    public MessageWriter(MessageRepository sharedMessageRepo) {
        this.messageRepo = sharedMessageRepo;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text.split("\n");

        // write lines of the song one by one, sleep random time betwean writes.
        // print "Finished" at the end to indicate lat message;
        for (int i = 0; i < lines.length; i++) {
            // write a message, the wait a bit
            // to allow the consumer opportunity to read it
            messageRepo.write(lines[i]);
            try {
                Thread.sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        messageRepo.write("Finished");
    }
}


