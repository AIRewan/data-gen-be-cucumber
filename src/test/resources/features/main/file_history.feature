Feature: File History Management
  As a user,
  I want the system to manage my file history
  So that I can see my recent files and prevent duplicate entries.

  Scenario: Replace a file with the same name
    Given a file named "<file_name>" with valid content is uploaded
    When a file named "<file_name>" with updated content is uploaded
    And the backend processes the request
    Then the status code should be 200
    And the response should include the uploaded filename and content
    And the response should show the original filename list

  Scenario: Save a successfully processed file to history
    Given a user uploads a file named "<file_name>"
    When the file is successfully processed
    Then the backend should update the file history with "<file_name>"
    And the backend should respond with a 200 status code

  Scenario: Show the last 3 successfully uploaded files
    Given the user's file history contains "<file_1>", "<file_2>"
    When the user uploads a file named "<file_3>"
    Then the backend should update the file history with "<file_3>"
    And the file history should display "<file_3>", "<file_2>", and "<file_1>"

  Scenario: Remove the oldest file when a new file is uploaded beyond history limit
    Given the user's file history contains "<file_1>", "<file_2>", and "<file_3>"
    When the user uploads a file named "<file_4>"
    Then the backend should remove "<file_1>" from the file history
    And the file history should display "<file_4>", "<file_3>", and "<file_2>"

  Scenario: Prevent duplicate files in the history
    Given the user's file history contains "<file_name>"
    When the user uploads a new file named "<file_name>"
    Then the backend should replace the content for "<file_name>" in the history
    And the backend should respond with a 200 status code

  Scenario: Clear all history files
    Given the user's file history contains "<file_1>", "<file_2>", and "<file_3>"
    When the user clicks on the "Clear" button
    Then the backend should clear all stored filenames and content
    And the backend should respond with a 200 status code and message "History cleared"