package com.bookstore.ui;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import com.bookstore.service.BookService;
import com.bookstore.service.OrderService;
import com.bookstore.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService = new BookService();
    private final UserService userService = new UserService();
    private final OrderService orderService = new OrderService();

    public void start(User admin) {
        while (true) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║   ADMIN MENU – Logged in as " + admin.getFullName() + " ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book Stock");
            System.out.println("4. List All Books");
            System.out.println("5. Low Stock Alerts");
            System.out.println("6. Update Book Details");
            System.out.println("7. View All Users");
            System.out.println("8. View Sales Report");
            System.out.println("0. Logout");
            System.out.print("Select option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": bookService.addBookCLI(scanner); pause(); break;
                case "2": bookService.removeBookCLI(scanner); pause(); break;
                case "3": bookService.updateStockCLI(scanner); pause(); break;
                case "4": listAllBooks(); break;
                case "5": lowStockAlerts(); break;
                case "6": updateBookDetails(); break;
                case "7": userService.viewAllUsers(); pause(); break;
                case "8": orderService.viewSalesReport(); pause(); break;
                case "0": System.out.println("Logging out Admin..."); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void listAllBooks() {
        List<Book> books = bookService.listAllBooksSortedByTitle();
        if (books.isEmpty()) { System.out.println("\nNo books found."); pause(); return; }
        System.out.println("\n--- ALL BOOKS ---");
        for (Book b : books) {
            System.out.printf("ID:%d | %s | %s | $%.2f | Stock:%d%n",
                    b.getBookId(), b.getTitle(), b.getAuthor(),
                    b.getPrice().doubleValue(), b.getStockQuantity());
        }
        pause();
    }

    private void lowStockAlerts() {
        System.out.print("Threshold (default 3): ");
        String t = scanner.nextLine().trim();
        int threshold = 3;
        try { if (!t.isEmpty()) threshold = Integer.parseInt(t); } catch (NumberFormatException ignored) {}
        List<Book> books = bookService.lowStockAlerts(threshold);
        if (books.isEmpty()) { System.out.println("\nNo low stock items."); pause(); return; }
        System.out.println("\n--- LOW STOCK ---");
        books.forEach(b -> System.out.printf("ID:%d | %s | Stock:%d%n",
                b.getBookId(), b.getTitle(), b.getStockQuantity()));
        pause();
    }

    private void updateBookDetails() {
        try {
            System.out.print("Book ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("New Price (leave blank to skip): ");
            String p = scanner.nextLine().trim();
            System.out.print("New Stock (leave blank to skip): ");
            String s = scanner.nextLine().trim();
            System.out.print("New Description (leave blank to skip): ");
            String d = scanner.nextLine().trim();
            Double price = p.isEmpty() ? null : Double.parseDouble(p);
            Integer stock = s.isEmpty() ? null : Integer.parseInt(s);
            String desc = d.isEmpty() ? null : d;
            boolean ok = bookService.updateBookDetails(id, price, stock, desc);
            System.out.println(ok ? "✓ Updated." : "Nothing updated / invalid ID.");
        } catch (Exception e) { System.out.println("Invalid input."); }
        pause();
    }

    private void pause() { System.out.print("\nPress Enter to continue..."); scanner.nextLine(); }
}
