Feature: Persisting User Data
  As a user,
  I want to return to the form page and see the data I previously filled
  So that I don’t have to re-enter the same information.

  Scenario: Pre-fill form with previously saved data
    Given the user successfully generated JSON based on the following input:
      | field       | value  |
      | data__name  | Alice  |
      | data__age   | 30     |
    When the user navigates back to the form page
    Then the form should be pre-filled with the following data:
      | field       | value  |
      | data__name  | Alice  |
      | data__age   | 30     |

  Scenario: Pre-fill form with partially filled data
    Given the user previously provided the following input during failed validation:
      | field       | value  |
      | data__name  | John   |
    When the user navigates back to the form page
    Then the form should be pre-filled with the following data:
      | field       | value  |
      | data__name  | John   |