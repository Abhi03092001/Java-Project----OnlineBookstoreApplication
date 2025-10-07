package com.bookstore.model;

import com.bookstore.model.Book;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Book, Integer> items = new LinkedHashMap<>();
    public Map<Book, Integer> getItems() { return items; }
}
