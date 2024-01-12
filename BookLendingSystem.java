// BookLendingSystem
import java.util.*;

public class BookLendingSystem implements InvariantCheck {

  public enum BookStatus {
    AVAILABLE, BORROWED, RESERVED
  }

  public enum MemberStatus {
    ACTIVE, INACTIVE
  }

  private static final int MAX_BOOKS_ALLOWED = 2; // Adjusted based on your provided code
  private Map<String, BookStatus> books = new HashMap<>();
  private Map<String, MemberStatus> members = new HashMap<>();
  private Map<String, Set<String>> memberBooks = new HashMap<>();

  public boolean inv() {
    for (Set<String> borrowed : memberBooks.values()) {
      if (borrowed.size() > MAX_BOOKS_ALLOWED) {
        return false;
      }
    }
    return true;
  }

  public void addBook(String isbn) {
    books.put(isbn, BookStatus.AVAILABLE);
  }

  public void addMember(String id) {
    members.put(id, MemberStatus.ACTIVE);
    memberBooks.put(id, new HashSet<>());
  }

  public void checkOutBook(String isbn, String memberId) {
    // Precondition checks
    preTest(books.containsKey(isbn), "Book not found.");
    preTest(members.containsKey(memberId), "Member not found.");
    preTest(books.get(isbn) == BookStatus.AVAILABLE, "Book is not available for checkout.");
    preTest(members.get(memberId) == MemberStatus.ACTIVE, "Member is INACTIVE.");
    preTest(canBorrow(memberId), "Member has reached borrowing limit.");

    // Process the checkout
    books.put(isbn, BookStatus.BORROWED);
    memberBooks.get(memberId).add(isbn);

    // Post-check invariant
    invTest();
  }

  public void returnBook(String isbn, String memberId) {
    preTest(books.containsKey(isbn), "Book not found.");
    preTest(members.containsKey(memberId), "Member not found.");
    preTest(books.get(isbn) == BookStatus.BORROWED, "Book is not borrowed.");
    books.put(isbn, BookStatus.AVAILABLE);
    memberBooks.get(memberId).remove(isbn);
    invTest();
  }

  public void updateMemberStatus(String memberId, MemberStatus newStatus) {
    preTest(members.containsKey(memberId), "Member not found.");
    members.put(memberId, newStatus);
    // If a member is set to INACTIVE, all borrowed books are returned automatically
    if (newStatus == MemberStatus.INACTIVE) {
      Set<String> borrowedBooks = new HashSet<>(memberBooks.get(memberId));
      for (String borrowedBook : borrowedBooks) {
        returnBook(borrowedBook, memberId);
      }
    }
    invTest();
  }

  private boolean canBorrow(String memberId) {
    return memberBooks.get(memberId).size() < MAX_BOOKS_ALLOWED;
  }

  private void preTest(boolean condition, String errorMessage) {
    if (!condition) {
      throw new RuntimeException(errorMessage);
    }
  }

  private void invTest() {
    if (!inv()) {
      throw new RuntimeException("Invariant failed.");
    }
  }
}
