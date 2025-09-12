Feature: File Upload and Validation
  As a user,
  I want to upload a YAML file and validate its contents
  So that I can proceed if the file is valid or receive appropriate error messages if it is not.

  Scenario: Upload and validate a valid file
    Given a user uploads a file named "<file_name>"
    When the backend processes the file
    Then the backend should respond with a 200 status code and message "File is valid"

  Scenario: Upload a file that exceeds size limits
    Given a user uploads a file with a size of "6MB"
    When the backend processes the file
    Then the backend should respond with a 413 status code and message "File is too large, 5MB max"

  Scenario: Reject a malformed file
    Given a user uploads a file named "<file_name>" with improper YAML structure
    When the backend processes the file
    Then the backend should respond with a 400 status code and message "File is not a valid OpenAPI specification"

  Scenario: Reject an empty file
    Given a user uploads a file with a size of "0 bytes"
    When the backend processes the file
    Then the backend should respond with a 400 status code and message "The uploaded file is empty"