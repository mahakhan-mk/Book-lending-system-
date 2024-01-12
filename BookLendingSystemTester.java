// BookLendingSystemTester.java
import java.util.Scanner;

public class BookLendingSystemTester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookLendingSystem library = new BookLendingSystem();

        System.out.println("Welcome to the Book Lending System");
        String input;
        do {
            System.out.println("\nSelect an action:");
            System.out.println("1: Add Book");
            System.out.println("2: Add Member");
            System.out.println("3: Check Out Book");
            System.out.println("4: Return Book");
            System.out.println("5: Update Member Status");
            System.out.println("exit: Exit the system");
            System.out.print("Your choice: ");
            input = scanner.nextLine();

            try {
                switch (input) {
                    case "1":
                        System.out.print("Enter book ISBN: ");
                        String isbn = scanner.nextLine();
                        library.addBook(isbn);
                        System.out.println("Book added.");
                        break;
                    case "2":
                        System.out.print("Enter member ID: ");
                        String id = scanner.nextLine();
                        library.addMember(id);
                        System.out.println("Member added.");
                        break;
                    case "3":
                        System.out.print("Enter book ISBN to check out: ");
                        isbn = scanner.nextLine();
                        System.out.print("Enter member ID: ");
                        id = scanner.nextLine();
                        library.checkOutBook(isbn, id);
                        System.out.println("Book checked out.");
                        break;
                    case "4":
                        System.out.print("Enter book ISBN to return: ");
                        isbn = scanner.nextLine();
                        System.out.print("Enter member ID: ");
                        id = scanner.nextLine();
                        library.returnBook(isbn, id);
                        System.out.println("Book returned.");
                        break;
                    case "5":
                        System.out.print("Enter member ID: ");
                        id = scanner.nextLine();
                        System.out.print("Enter new status (ACTIVE/INACTIVE): ");
                        String status = scanner.nextLine();
                        library.updateMemberStatus(id, BookLendingSystem.MemberStatus.valueOf(status.toUpperCase()));
                        System.out.println("Member status updated.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!input.equalsIgnoreCase("exit"));

        scanner.close();
        System.out.println("Thank you for using the Book Lending System.");
    }
}
