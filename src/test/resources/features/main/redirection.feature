Feature: File API Redirection
  As a user,
  I want the backend to handle redirection appropriately
  So that I am only redirected after uploading a valid file.

  Scenario: Redirect on valid file upload
    Given a user uploads a file named "<file_name>"
    When the backend processes the file
    Then the backend should store the file "<file_name>" and its content
    And the backend should respond with a 302 status code and redirect to the "form" page

  Scenario: Do not redirect on invalid file upload
    Given a user uploads a file named "<file_name>"
    When the backend processes the file
    Then the backend should respond with a 400 status code and message "<error_message>"

  Scenario: Redirect with stored file content on subsequent visits
    Given the user has previously uploaded and validated a file named "<file_name>"
    When the user visits the main page
    And the user presses the "Go" button
    Then the backend should retrieve "<file_name>" from local storage
    And the backend should respond with a 302 status code and redirect to the "form" page with the file content