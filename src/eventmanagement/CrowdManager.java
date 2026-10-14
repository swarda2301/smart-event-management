package eventmanagement;

public class CrowdManager {
    public void displayCrowdStatus(Venue venue) {
        double percentage = venue.getOccupancyPercentage();

        System.out.printf("Occupancy: %d/%d (%.1f%%)%n",
                venue.getCurrentOccupancy(),
                venue.getMaximumCapacity(),
                percentage);

        if (percentage >= 100) {
            System.out.println("Status: FULL - ENTRY CLOSED");
        } else if (percentage >= 90) {
            System.out.println("Status: CRITICAL - IMMEDIATE CROWD ALERT");
        } else if (percentage >= 80) {
            System.out.println("Status: HIGH CROWD - WARNING");
        } else {
            System.out.println("Status: NORMAL");
        }
    }
}
