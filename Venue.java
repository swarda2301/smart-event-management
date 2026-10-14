package eventmanagement;

public class Venue {
    private final String venueId;
    private final String venueName;
    private final int maximumCapacity;
    private int currentOccupancy;

    public Venue(String venueId, String venueName, int maximumCapacity) {
        if (maximumCapacity <= 0) {
            throw new IllegalArgumentException("Venue capacity must be positive.");
        }
        this.venueId = venueId;
        this.venueName = venueName;
        this.maximumCapacity = maximumCapacity;
        this.currentOccupancy = 0;
    }

    public String getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public boolean isFull() {
        return currentOccupancy >= maximumCapacity;
    }

    public void increaseOccupancy() {
        if (isFull()) {
            throw new IllegalStateException("Venue capacity reached.");
        }
        currentOccupancy++;
    }

    public void decreaseOccupancy() {
        if (currentOccupancy > 0) {
            currentOccupancy--;
        }
    }

    public double getOccupancyPercentage() {
        return (currentOccupancy * 100.0) / maximumCapacity;
    }
}
