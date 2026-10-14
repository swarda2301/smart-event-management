package eventmanagement;

import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {
    private final Queue<Attendee> waitingQueue = new LinkedList<>();

    public void addToQueue(Attendee attendee) {
        if (attendee == null) {
            throw new IllegalArgumentException("Attendee cannot be null.");
        }
        waitingQueue.offer(attendee);
    }

    public Attendee removeFromQueue() {
        return waitingQueue.poll();
    }

    public Attendee getNextAttendee() {
        return waitingQueue.peek();
    }

    public boolean isEmpty() {
        return waitingQueue.isEmpty();
    }

    public int size() {
        return waitingQueue.size();
    }

    public void displayQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.println("\n--- Waiting Queue ---");
        int position = 1;
        for (Attendee attendee : waitingQueue) {
            System.out.println(position++ + ". " + attendee.getName()
                    + " [" + attendee.getUserId() + "]");
        }
    }
}
