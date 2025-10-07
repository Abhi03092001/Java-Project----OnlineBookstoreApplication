//package com.bookstore;
//
//import com.bookstore.model.User;
//import com.bookstore.service.UserService;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class UserServiceTest {
//    @Test
//    public void testRegisterAndLogin() {
//        UserService service = new UserService();
//        User u = service.register("testuser", "password", "test@example.com", "Test User");
//        assertNotNull(u);
//        assertEquals("testuser", u.getUsername());
//        User loggedIn = service.login("testuser", "password");
//        assertNotNull(loggedIn);
//    }
//}
