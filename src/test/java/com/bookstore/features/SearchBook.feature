Feature: Search for a book in the bookstore

  Scenario: User searches a book by title
    Given the bookstore has 'Clean Code'
    When the user searches for 'Clean Code'
    Then the system shows 'Clean Code' with author name
