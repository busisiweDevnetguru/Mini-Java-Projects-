import java.util.*;

public class PasswordGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;

        // Step 1: Ask user how many words (2 or 3)
		//the || checks two conditions and returns:true if at least one of the conditions is true or false only if both conditions are false
        while (count < 2 || count > 3) {
            System.out.print("How many words would you like to enter (2 or 3)? ");
            if (scanner.hasNextInt()) {
                count = scanner.nextInt();
                if (count < 2 || count > 3) {
                    System.out.println("Please enter 2 or 3 only.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
                scanner.next(); // clear invalid input
            }
        }

        scanner.nextLine(); // Consume leftover newline

        String[] words = new String[count];

        // Step 2: Get user input for words and format them
        for (int i = 0; i < count; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            String input = scanner.nextLine().trim();

            // Capitalize first letter and make rest lowercase
            if (!input.isEmpty()) {
                words[i] = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
            } else {
                words[i] = "";
            }
        }

        // Step 3: Sort the words alphabetically
        Arrays.sort(words);
        System.out.println("\nSorted Words:");
        for (int i = 0; i < words.length; i++) {
            System.out.println((i + 1) + ". " + words[i]);
        }

        // Loop until the user is happy with the password
        boolean satisfied = false;
        while (!satisfied) {
            // Step 4: Ask user to select two words by number
            int first = 0, second = 0;
            while (first < 1 || first > count || second < 1 || second > count || first == second) {
                System.out.print("\nChoose two different word numbers (e.g., 1 2): ");
                if (scanner.hasNextInt()) {
                    first = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        second = scanner.nextInt();
                    } else {
                        second = 0;
                        scanner.next(); // Clear invalid input
                    }
                } else {
                    scanner.next(); // Clear invalid input
                }
            }

            String word1 = words[first - 1];
            String word2 = words[second - 1];

            // Step 5: User chooses a special symbol
            System.out.print("Choose a special character (e.g., @, #, !): ");
            scanner.nextLine(); // consume leftover newline
            String special = scanner.nextLine();
            special = special.isEmpty() ? "@" : special; // default if empty

            // Step 6: Create password:
            // Reverse word1
            String reversed = new StringBuilder(word1).reverse().toString();
            // First 3 letters of word2
            String partial = word2.length() >= 3 ? word2.substring(0, 3) : word2;
            // Combine parts + special + total length
            int totalLength = word1.length() + word2.length();
            String password = reversed + special + partial + totalLength;

            // Step 7: Check if password starts with a vowel
            char firstChar = password.toLowerCase().charAt(0);
            boolean startsWithVowel = "aeiou".indexOf(firstChar) != -1;

            // Step 8: Show result
            String result = String.format(
                "\nYour generated password is: %s\nStarts with a vowel? %s",
                password,
                (startsWithVowel ? "Yes" : "No")
            );
            System.out.println(result);

            // Step 9: Ask if they are satisfied
            System.out.print("Do you want to keep this password? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            satisfied = response.equals("yes") || response.equals("y");
        }

        System.out.println("\n✔ Password finalized. Stay secure out there!");
        scanner.close();
    }
}
