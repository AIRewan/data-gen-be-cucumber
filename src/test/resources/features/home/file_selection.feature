Feature: File Selection
  As a user,
  I want to select a YAML file for upload
  So that the backend processes my file correctly.

  Scenario: Select a valid file for processing
    Given a user uploads a file named "<file_name>"
    When the backend processes the file
    Then the file name and content should be stored temporarily
    And the backend should respond with a 200 status code

  Scenario: Attempt to process "Select a file" menu item
    Given a user selects "Select a file"
    When the backend processes the file
    Then the backend should respond with a 400 status code and message "No file selected"

  Scenario: Replace an existing selected file with the same name
    Given a user has previously uploaded a file named "<file_name>"
    When the user selects and uploads a new file named "<file_name>"
    Then the backend should replace the stored filename and content with "<file_name>"
    And the backend should respond with a 200 status code