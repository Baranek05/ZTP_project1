Feature: Products management

  Scenario: Add a new product with correct data
    Given the product name is "Headphones"
    And the product price is 150
    And the product quantity is 50
    When I save the new product
    Then the product should be saved successfully


