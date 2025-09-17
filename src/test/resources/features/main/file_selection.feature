Feature: File Selection
  As a user,
  I want to select a YAML file for upload
  So that the backend processes my file correctly.

  Scenario: Replace a file with the same name
    Given a file named "<file_name>" with valid content is uploaded
    When a file named "<file_name>" with updated content is uploaded
    And the backend processes the request
    Then the status code should be 200
    And the response should include the uploaded filename and content
    And the response should show the original filename list