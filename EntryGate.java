package eventmanagement;

public class EntryGate {
    private final String gateId;
    private final String gateName;
    private final int capacity;
    private int currentCount;

    public EntryGate(String gateId, String gateName, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Gate capacity must be positive.");
        }
        this.gateId = gateId;
        this.gateName = gateName;
        this.capacity = capacity;
        this.currentCount = 0;
    }

    public String getGateId() {
        return gateId;
    }

    public String getGateName() {
        return gateName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public boolean isAvailable() {
        return currentCount < capacity;
    }

    public void allowEntry() {
        if (!isAvailable()) {
            throw new IllegalStateException("Gate " + gateName + " is full.");
        }
        currentCount++;
    }

    public void exitAttendee() {
        if (currentCount > 0) {
            currentCount--;
        }
    }

    public void displayStatus() {
        System.out.println(gateName + " (" + gateId + ") : "
                + currentCount + "/" + capacity);
    }
}
