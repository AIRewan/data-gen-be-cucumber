Feature: Form Validation
  As a user,
  I want the backend to validate my inputs
  So that I can correct any missing or incorrect data before generating the JSON.

  Scenario: Validate a request with all fields filled
    Given the form structure includes:
      | field       | type   | example | mandatory |
      | data__name  | string | Anna    | true      |
      | data__age   | int    | 36      | false     |
    And the user provides:
      | field       | value  |
      | data__name  | Alice  |
      | data__age   | 30     |
    When the backend validates the input
    Then the validation should pass
    And the backend should respond with a 200 status code

  Scenario: Missing mandatory fields
    Given the form structure includes:
      | field       | type   | example | mandatory |
      | data__name  | string | Anna    | true      |
      | data__age   | int    | 36      | false     |
    And the user provides:
      | field       | value  |
      | data__age   | 30     |
    When the backend validates the input
    Then the validation should fail
    And the backend should respond with a 400 status code
    And the response should include the message "Missing required fields: data__name"

  Scenario: Invalid field type
    Given the form structure includes:
      | field       | type   | example | mandatory |
      | data__name  | string | Anna    | true      |
      | data__age   | int    | 36      | false     |
    And the user provides:
      | field       | value  |
      | data__name  | Alice  |
      | data__age   | invalid_value |
    When the backend validates the input
    Then the validation should fail
    And the backend should respond with a 400 status code
    And the response should include the message "Invalid value for data__age, expected type int"