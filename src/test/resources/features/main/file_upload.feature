Feature: File Upload
  As a user,
  I want the request to always include the current filename, the file content,
  and the list of previously uploaded filenames.

  Scenario: Validate the request structure
    Given a file named "<file_name>" with valid content is uploaded
    And the file history is ["prev_file1.yaml", "prev_file2.yaml"]
    When the backend processes the request
    Then the request data should include:
      | key | value |
      | filename | "<file_name>" |
      | content | "This is valid file content" |
      | history | ["prev_file1.yaml", "prev_file2.yaml"] |
    And the response should include:
      | key | value |
      | filename | "<file_name>" |
      | content | "This is valid file content" |
      | history | ["file_name", "prev_file1.yaml", "prev_file2.yaml"] |

  Scenario: Handle history for generic invalid file upload
    Given a file upload fails due to validation issues
    And the file history is ["prev_file1.yaml", "prev_file2.yaml"]
    Then the response should include:
      | key      | value                          |
      | filename | "<file_name>"                 |
      | content  | ""                            |
      | history  | ["prev_file1.yaml", "prev_file2.yaml"] |

  Scenario: Upload a valid file
    Given a file named "<file_name>" with valid content is uploaded
    When the backend processes the request
    Then the status code should be 200
    And the response should include the uploaded filename and content

  Scenario: Upload a file that exceeds size limits
    Given a file with a size of "6MB" is uploaded
    When the backend processes the request
    Then Then the response should include a 413 status code and message "File is too large, 5MB max"
    And should follow "Handle history for generic invalid file upload" history behaviour

  Scenario: Reject a malformed file
    Given a file named "<file_name>" with improper YAML structure is uploaded
    When the backend processes the request
    Then the response should include a 400 status code and message "File is not a valid OpenAPI specification"
    And should follow "Handle history for generic invalid file upload" history behaviour

  Scenario: Reject an empty file
    Given a file with a size of "0 bytes" is uploaded
    When the backend processes the request
    Then the response should include a 400 status code and message "The uploaded file is empty"
    And should follow "Handle history for generic invalid file upload" history behaviour