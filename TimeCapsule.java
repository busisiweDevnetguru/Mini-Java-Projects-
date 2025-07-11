// Import necessary Java time and utility classes
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class WorldClockCapsuleAdventure {

    // Inner class to represent a single time capsule
    static class TimeCapsule {
        private String title;
        private String message;
        private LocalDate date;
        private LocalTime time;
        private ZoneId zone;
        private boolean reminderSet;

        // Constructor to initialize a time capsule
        public TimeCapsule(String title, String message, LocalDate date, LocalTime time, ZoneId zone, boolean reminderSet) {
            this.title = title;
            this.message = message;
            this.date = date;
            this.time = time;
            this.zone = zone;
            this.reminderSet = reminderSet;
        }

        // Getters to access capsule data
        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public LocalDate getDate() { return date; }
        public LocalTime getTime() { return time; }
        public ZoneId getZone() { return zone; }
        public boolean isReminderSet() { return reminderSet; }

        // Formats capsule details using the chosen locale
        public String toFormattedString(Locale locale) {
            ZonedDateTime capsuleDateTime = ZonedDateTime.of(date, time, zone);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm zzzz", locale);
            return String.format("Capsule: %s\nMessage: %s\nDate/Time: %s\nReminder: %s\n",
                    title, message, capsuleDateTime.format(formatter), reminderSet ? "Yes" : "No");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<TimeCapsule> capsules = new ArrayList<>();

        System.out.println("Welcome to World Clock Capsule Adventure!");

        // Ask user how many capsules to create
        System.out.print("How many capsules to create? ");
        int count = Integer.parseInt(scanner.nextLine());

        // Loop to create each capsule
        for (int i = 0; i < count; i++) {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Message: ");
            String message = scanner.nextLine();

            System.out.print("Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Time (HH:mm): ");
            LocalTime time = LocalTime.parse(scanner.nextLine());

            System.out.print("Zone (e.g., Africa/Johannesburg): ");
            ZoneId zone = ZoneId.of(scanner.nextLine());

            System.out.print("Reminder? (true/false): ");
            boolean reminderSet = Boolean.parseBoolean(scanner.nextLine());

            capsules.add(new TimeCapsule(title, message, date, time, zone, reminderSet));
        }

        // Ask user to choose a locale for formatting
        System.out.print("Choose a locale (e.g., FRANCE, JAPAN, ENGLISH): ");
        Locale locale = Locale.forLanguageTag(scanner.nextLine());

        System.out.println("\n--- All Capsules ---");
        for (TimeCapsule capsule : capsules) {
            // Print formatted capsule details
            System.out.println(capsule.toFormattedString(locale));

            // Calculate time difference
            ZonedDateTime now = ZonedDateTime.now(capsule.getZone());
            ZonedDateTime future = ZonedDateTime.of(capsule.getDate(), capsule.getTime(), capsule.getZone());

            // Period for days, ChronoUnit for minutes
            Period period = Period.between(now.toLocalDate(), future.toLocalDate());
            long minutes = ChronoUnit.MINUTES.between(now, future);

            System.out.printf("Days remaining: %d days\n", period.getDays());
            System.out.printf("Minutes remaining: %d mins\n", minutes);
            System.out.println();
        }

        // Optional filtering by zone or date
        System.out.print("Filter by Zone ID or Date (yyyy-MM-dd)? ");
        String filter = scanner.nextLine();

        System.out.println("--- Filtered Capsules ---");
        for (TimeCapsule capsule : capsules) {
            if (capsule.getZone().getId().equalsIgnoreCase(filter) ||
                capsule.getDate().toString().equals(filter)) {
                System.out.println(capsule.toFormattedString(locale));
            }
        }

        // Close scanner
        scanner.close();
    }
}
