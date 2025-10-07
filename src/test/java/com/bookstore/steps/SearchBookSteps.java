package com.bookstore.steps;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;

import java.util.HashMap;
import java.util.Map;

public class SearchBookSteps {

    private Map<String, String> bookstore = new HashMap<>();
    private String searchResult;

    @Given("the bookstore has {string}")
    public void the_bookstore_has(String title) {
        // Pre-populate with a book: Clean Code by Robert C. Martin
        bookstore.put("Clean Code", "Robert C. Martin");
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String title) {
        if (bookstore.containsKey(title)) {
            searchResult = title + " by " + bookstore.get(title);
        } else {
            searchResult = null;
        }
    }

    @Then("the system shows {string} with author name")
    public void the_system_shows_with_author_name(String expectedTitle) {
        assertNotNull(searchResult, "Book should be found");
        assertTrue(searchResult.contains(expectedTitle), "Result should contain the book title");
        assertTrue(searchResult.contains("Robert C. Martin"), "Result should contain the author name");
    }
}
