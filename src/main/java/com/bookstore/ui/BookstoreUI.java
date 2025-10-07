package com.bookstore.ui;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import com.bookstore.service.*;
import com.bookstore.service.DynamoDBRecommendationService;
import com.bookstore.util.ReceiptGenerator;
import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BookstoreUI {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();
    private final BrowsingHistoryService historyService = new BrowsingHistoryService();
    private final RecommendationService recommendationService = new RecommendationService();
    private User currentUser;

    public void start() {
        displayWelcomePage();
    }

    // ---------------- Welcome / Authentication ---------------- //

    private void displayWelcomePage() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   WELCOME TO ONLINE BOOKSTORE          ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Enter Bookstore");
            System.out.println("0. Exit");
            System.out.print("\nSelect option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    displayAuthenticationPage();
                    break;
                case "0":
                    System.out.println("\nThank you for visiting! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayAuthenticationPage() {
        while (currentUser == null) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         AUTHENTICATION                 ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Sign In (Customer)");
            System.out.println("2. Sign Up");
            System.out.println("3. Enter as Guest");
            System.out.println("4. Admin Login");
            System.out.print("\nSelect option (1-4, 0 to Back): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    signIn(false);
                    break;
                case "2":
                    signUp();
                    break;
                case "3":
                    enterAsGuest();
                    break;
                case "4":
                    signIn(true);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        if (currentUser != null) {
            if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                new AdminUI().start(currentUser);
            } else {
                displayMainMenu();
            }
            currentUser = null;
        }
    }

    private void signIn(boolean adminLogin) {
        System.out.println("\n--- SIGN IN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userService.login(username, password);
        if (user != null) {
            if (adminLogin && !"ADMIN".equalsIgnoreCase(user.getRole())) {
                System.out.println("Access denied! This is not an Admin account.");
                pauseScreen();
                return;
            }
            this.currentUser = user;
            System.out.println("\n✓ Login successful (Role: " + user.getRole() + ")");
            System.out.println("Welcome, " + user.getFullName() + "!");
        } else {
            System.out.println("\n✗ Login failed. Please check your credentials.");
        }
        pauseScreen();
    }

    private void signUp() {
        System.out.println("\n--- SIGN UP ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        if (userService.usernameExists(username)) {
            System.out.println("Username already exists. Please choose another.");
            pauseScreen();
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine().trim();

        currentUser = userService.register(username, password, email, fullName);
        if (currentUser != null) {
            System.out.println("\n✓ Registration successful! You are now logged in.");
            System.out.println("Welcome, " + currentUser.getFullName() + "!");
        } else {
            System.out.println("\n✗ Registration failed. Please try again.");
        }
        pauseScreen();
    }

    private void enterAsGuest() {
        currentUser = User.createGuest();
        System.out.println("\n✓ Entered as Guest. Note: Some features are limited.");
        pauseScreen();
    }

    // ---------------- Main Menu ---------------- //

    private void displayMainMenu() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   MAIN MENU – Logged in as " + currentUser.getFullName() + " ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Search Books");
            System.out.println("2. Browse All Books");
            System.out.println("3. View Book Details");
            System.out.println("4. View Cart (" + cartService.getItemCount() + " items)");
            System.out.println("5. Checkout");
            if (!currentUser.isGuest()) {
                System.out.println("6. View Order History");
            }
            System.out.println("7. View Browsing History");
            System.out.println("8. View Recommendations");
            System.out.println("0. Logout");
            System.out.print("\nSelect option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    searchBooks();
                    break;
                case "2":
                    browseAllBooks();
                    break;
                case "3":
                    viewBookDetails();
                    break;
                case "4":
                    viewCart();
                    break;
                case "5":
                    checkout();
                    break;
                case "6":
                    if (!currentUser.isGuest()) {
                        viewOrderHistory();
                    } else {
                        System.out.println("This feature is not available for guests.");
                    }
                    break;
                case "7":
                    viewBrowsingHistory();
                    break;
                case "8":
                    viewRecommendations();
                    break;
                case "0":
                    logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ---------------- Book Actions ---------------- //

    private void searchBooks() {
        System.out.println("\n--- SEARCH BOOKS ---");
        System.out.print("Enter search term (title/author/category): ");
        String searchTerm = scanner.nextLine().trim();

        List<Book> results = bookService.searchBooks(searchTerm);
        if (results.isEmpty()) {
            System.out.println("\nNo books found matching: " + searchTerm);
        } else {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
            System.out.print("\nEnter book number to view details (0 to cancel): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice > 0 && choice <= results.size()) {
                    Book selectedBook = results.get(choice - 1);
                    historyService.addToHistory(selectedBook);
                    displayBookDetailsAndActions(selectedBook);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        pauseScreen();
    }

    private void browseAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("\nNo books available.");
            pauseScreen();
            return;
        }

        int page = 0, pageSize = 10;
        int totalPages = (int) Math.ceil((double) books.size() / pageSize);

        while (true) {
            System.out.println("\n--- ALL BOOKS (Page " + (page + 1) + "/" + totalPages + ") ---");
            int start = page * pageSize;
            int end = Math.min(start + pageSize, books.size());
            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
            System.out.println("\nOptions:");
            System.out.println("N - Next Page | P - Previous Page");
            System.out.println("Enter book number to view details | 0 - Back to menu");
            System.out.print("\nYour choice: ");

            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("N") && page < totalPages - 1) {
                page++;
            } else if (input.equals("P") && page > 0) {
                page--;
            } else if (input.equals("0")) {
                break;
            } else {
                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= books.size()) {
                        Book selectedBook = books.get(choice - 1);
                        historyService.addToHistory(selectedBook);
                        displayBookDetailsAndActions(selectedBook);
                    } else {
                        System.out.println("Invalid book number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    private void viewBookDetails() {
        System.out.print("\nEnter Book ID: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine().trim());
            Book book = bookService.getBookById(bookId);
            if (book != null) {
                historyService.addToHistory(book);
                displayBookDetailsAndActions(book);
            } else {
                System.out.println("Book not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
        }
        pauseScreen();
    }

    private void displayBookDetailsAndActions(Book book) {
        System.out.println(book.toDetailedString());
        System.out.println("\nActions:");
        System.out.println("1. Add to Cart");
        System.out.println("0. Back");
        System.out.print("\nSelect option: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            System.out.print("Enter quantity: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity > 0) {
                    cartService.addBook(book, quantity);
                } else {
                    System.out.println("Invalid quantity.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        pauseScreen();
    }

    // ---------------- Cart ---------------- //

    private void viewCart() {
        cartService.displayCart();
        if (cartService.isEmpty()) {
            pauseScreen();
            return;
        }

        System.out.println("Options:");
        System.out.println("1. Update Quantity");
        System.out.println("2. Remove Item");
        System.out.println("3. Clear Cart");
        System.out.println("0. Back");
        System.out.print("\nSelect option: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                updateCartQuantity();
                break;
            case "2":
                removeFromCart();
                break;
            case "3":
                cartService.clearCart();
                pauseScreen();
                break;
        }
    }

    private void updateCartQuantity() {
        System.out.print("Enter Book ID: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine().trim());
            Book book = bookService.getBookById(bookId);
            if (book != null && cartService.getCart().containsKey(book)) {
                System.out.print("Enter new quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                cartService.updateQuantity(book, quantity);
            } else {
                System.out.println("Book not found in cart.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        pauseScreen();
    }

    private void removeFromCart() {
        System.out.print("Enter Book ID to remove: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine().trim());
            Book book = bookService.getBookById(bookId);
            if (book != null) {
                cartService.removeBook(book);
            } else {
                System.out.println("Book not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        pauseScreen();
    }

    // ---------------- Checkout ---------------- //

    private void checkout() {
        if (currentUser.isGuest()) {
            System.out.println("\nGuests cannot checkout. Please sign in or sign up.");
            pauseScreen();
            return;
        }
        if (cartService.isEmpty()) {
            System.out.println("\nYour cart is empty. Add some books first!");
            pauseScreen();
            return;
        }

        cartService.displayCart();
        System.out.print("\nProceed with checkout? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        if (confirm.equals("Y")) {
            System.out.print("Enter Shipping Address: ");
            String address = scanner.nextLine().trim();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine().trim();
            int billNo = 100000 + new Random().nextInt(900000);

            var order = orderService.createOrder(currentUser.getUserId(), cartService.getCart());
            if (order != null) {
                recommendationService.generateRecommendationsBasedOnHistory(
                        currentUser.getUserId(), historyService.getHistory());

                // Print Bill
                System.out.println("\n========================================");
                System.out.println("              BOOKSTORE BILL            ");
                System.out.println("========================================");
                System.out.println("Bill No: " + billNo);
                System.out.println("Customer: " + currentUser.getFullName());
                System.out.println("Phone: " + phone);
                System.out.println("Address: " + address);
                System.out.println("----------------------------------------");
                cartService.getCart().forEach((book, qty) -> {
                    System.out.printf("%s x%d - $%.2f\n",
                            book.getTitle(), qty, book.getPrice().doubleValue() * qty);
                });
                System.out.println("----------------------------------------");
                System.out.printf("TOTAL: $%.2f\n", order.getTotalAmount());
                System.out.println("========================================");
                System.out.println("Thank you for shopping with us!");
                System.out.println("========================================");

                try {
                    String pdf = ReceiptGenerator.generatePdf(
                            billNo, currentUser, cartService.getCart(), address, phone, order.getTotalAmount());
                    String txt = ReceiptGenerator.generateText(
                            billNo, currentUser, cartService.getCart(), address, phone, order.getTotalAmount());
                    System.out.println("Saved receipt: " + pdf);
                    System.out.println("Saved receipt: " + txt);
                } catch (IOException | DocumentException e) {
                    System.err.println("Failed to save receipts: " + e.getMessage());
                }
                cartService.clearCart();
            } else {
                System.out.println("\n✗ Checkout failed. Please try again.");
            }
        } else {
            System.out.println("Checkout cancelled.");
        }
        pauseScreen();
    }

    // ---------------- History & Recommendations ---------------- //

    private void viewOrderHistory() {
        var orders = orderService.getOrderHistory(currentUser.getUserId());
        orderService.displayOrderHistory(orders);
        pauseScreen();
    }

    private void viewBrowsingHistory() {
        historyService.displayHistory();
        pauseScreen();
    }

    private void viewRecommendations() {
        if (currentUser.isGuest()) {
            System.out.println("\nRecommendations are not available for guests.");
            pauseScreen();
            return;
        }

        DynamoDBRecommendationService ddb = new DynamoDBRecommendationService();
        var recs = ddb.getTop5(currentUser.getUsername());
        if (recs == null || recs.isEmpty()) {
            System.out.println("\nNo personalized recommendations found in DynamoDB.");
        } else {
            System.out.println("\n=== YOUR TOP 5 RECOMMENDATIONS ===");
            int i = 1;
            for (var r : recs) {
                System.out.printf("%d. %s (score: %.2f)%n", i++, r.getTitle(), r.getScore());
            }
        }

        recommendationService.displayRecommendations(currentUser.getUserId());
        pauseScreen();
    }

    // ---------------- Logout & Utility ---------------- //

    private void logout() {
        System.out.println("\nLogging out...");
        currentUser = null;
        cartService.clearCart();
        System.out.println("✓ Logged out successfully!");
        pauseScreen();
    }

    private void pauseScreen() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
