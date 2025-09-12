Feature: Form Structure
  As a user,
  I want the backend to generate a form dynamically from the API specification
  So that I can see and interact with the fields required by the API.

  Scenario: Generate a form based on API specification
    Given the acceptable data based on the API specification is:
    """
    {
      "data": {
        "name": "string",
        "age": "int"
      }
    }
    """
    When the backend parses the specification
    Then the backend should generate the following form structure:
      | field          | type   | example | mandatory |
      | data__name     | string | Anna    | true      |
      | data__age      | int    | 36      | false     |

  Scenario: Highlight mandatory fields
    Given the acceptable data based on the API specification includes a mandatory field:
    """
    {
      "data": {
        "name": "string"
      }
    }
    """
    When the backend processes the specification
    Then the generated form should mark "data__name" as mandatory
    And the backend should include "mandatory": true in the response

  Scenario: Handle nested data structures
    Given the acceptable data based on the API specification includes a nested structure:
    """
    {
      "data": {
        "user": {
          "first_name": "string",
          "last_name": "string"
        },
        "age": "int"
      }
    }
    """
    When the backend parses the specification
    Then the backend should generate the following form structure:
      | field                 | type   | example        | mandatory |
      | data__user__first_name| string | John           | true      |
      | data__user__last_name | string | Doe            | false     |
      | data__age             | int    | 25             | false     |