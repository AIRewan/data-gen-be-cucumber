Feature: JSON Generation
  As a user,
  I want the backend to construct a valid JSON from my input
  So that I can see the generated JSON on the "Generated" page.

  Scenario: Successful JSON generation with all required fields
    Given the form structure includes:
      | field       | type   | example | mandatory |
      | data__name  | string | Anna    | true      |
      | data__age   | int    | 36      | false     |
    And the user provides:
      | field       | value  |
      | data__name  | Alice  |
      | data__age   | 30     |
    When the backend generates the JSON
    Then the backend should generate the following JSON:
    """
    {
      "data": {
        "name": "Alice",
        "age": 30
      }
    }
    """
    And the backend should redirect the user to the "Generated" page with this JSON