//package com.bookstore;
//
//import com.bookstore.model.Book;
//import com.bookstore.service.CartService;
//import org.junit.Test;
//import java.math.BigDecimal;
//import static org.junit.Assert.*;
//
//public class CartServiceTest {
//    @Test
//    public void testCartOperations() {
//        CartService cart = new CartService();
//        Book b = new Book();
//        b.setTitle("Test Book");
//        b.setPrice(new BigDecimal("10.00"));
//        cart.addBook(b, 2);
//        assertEquals(2, cart.getItemCount());
//        assertTrue(cart.calculateTotal().compareTo(new BigDecimal("20.00")) == 0);
//    }
//}
