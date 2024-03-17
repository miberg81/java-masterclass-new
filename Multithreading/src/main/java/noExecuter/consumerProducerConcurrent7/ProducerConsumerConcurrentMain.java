package noExecuter.consumerProducerConcurrent7;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerConcurrentMain {

    public static void main(String[] args) {

        // the resource shared by both threads
        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository), "Reader");
        Thread writer = new Thread(new MessageWriter(messageRepository), "Writer");

        // shut down thread when another thread has exception
        writer.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println("Writer had exception: " + e);
            if (reader.isAlive()) {
                System.out.println("Going to interrupt the reader");
                reader.interrupt();
            }
        });

        reader.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println("Writer had exception: " + e);
            if (writer.isAlive()) {
                System.out.println("Going to interrupt the writer");
                writer.interrupt();
            }
        });

        reader.start();
        writer.start();
    }
}

/*
Shared resource for all threads
 */
class MessageRepository {
    private String message;

    // This is the explicit lock. To use it we must manually lock and unlock
    private Lock lock = new ReentrantLock();

    // Indicates whether there is job to do
    // When false = producer can populate the field
    // When the message is true - the consumer can read it.
    private boolean hasMessage = false;

    public void write(String message) {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    // do nothing while already exists message that was not consumed yet
                    // exit the loop means message was read(consumed)
                    while (hasMessage) {
                        try {
                          // only check message status one in half second instead as much as possible
                          Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    hasMessage = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("** write blocked" + lock);
                hasMessage = true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // here we write new message
        this.message = message;
    }

    //  when consumer calls read, it will wait
    // in the loop until there is a message to read
    public String read() {
        // check if another consumer has already got this lock
        // if will get lock and return true if nobody got this lock yet
        if (lock.tryLock()) {
            try {
                while (!hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                hasMessage = false; // set to "false" to allow producer write next message
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("** read blocked " + lock);
            hasMessage = false;
        }
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
                    "\nHumpty Dumpty had a gerat fall," +
                    "\nAll the king's horses and all the king's men," +
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

