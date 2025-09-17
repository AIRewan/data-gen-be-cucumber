package testdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ConfigManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static utils.CommonUtils.pushFromLeft;

public class MainUploadSteps {
    private static final Logger logger = LoggerFactory.getLogger(MainUploadSteps.class);

    private final String baseUrl = ConfigManager.getProperty("base.url");
    private final String uploadEndpoint = ConfigManager.getProperty("upload.endpoint");

    private String fileName;
    private String fileContent;
    private String [] fileHistory = new String [3];
    private Response response;

    private void prepareData(String fileName, String content) {
        logger.info(()->"Preparing to upload a file with name: " + fileName);
        this.fileName = fileName;
        logger.debug(()->"File content: " + content);
        this.fileContent = content;
    }

    private void prepareJson() throws JsonProcessingException {
        Map<String, Object> jsonContent = new HashMap<>();
        jsonContent.put("filename", fileName);
        jsonContent.put("content", fileContent);
        jsonContent.put("history", fileHistory);

        ObjectMapper objectMapper = new ObjectMapper();
        this.fileContent = objectMapper.writeValueAsString(jsonContent);

        logger.debug(()->"Generated file content JSON payload: " + fileContent);
    }

    @Given("a file named {string} with valid content is uploaded")
    public void aFileNamedWithValidContentIsUploaded(String fileName) throws JsonProcessingException {
        this.fileName = fileName;
        this.fileContent = "This is the content";
    }

    @When("the backend processes the request")
    public void theBackendProcessesTheFile() throws JsonProcessingException {
        logger.info(()-> "Sending POST request to backend - URL: " + baseUrl + uploadEndpoint);
        prepareJson();
        
        this.response = RestAssured.given()
                .contentType("application/json")
                .body(fileContent)
                .post(baseUrl+uploadEndpoint);
        logger.info(()-> "Response received with status code: " + response.getStatusCode());
    }

    @Then("the status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode, actualStatusCode);

        logger.debug(()->"Actual status code: " + actualStatusCode);
    }

    @And("the response should include the uploaded filename and content")
    public void verifyResponseContent() {
        logger.info(()->"Validating the response content");
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        // Validate filename and file content
        String returnedFileName = jsonPath.getString("fileName");
        String returnedContent = jsonPath.getString("content");

        logger.debug(()->"Response body:\n" + responseBody);
        logger.debug(()->"Returned file name: " + returnedFileName + ", Returned file content: " + returnedContent);

        assertEquals(fileName, returnedFileName);
        assertEquals(fileContent, returnedContent);
        logger.info(()->"Response content has been successfully validated");
    }
    
    @When("a file named {string} with updated content is uploaded")
    public void aFileNamedWithUpdatedContentIsUploaded(String fileName) throws JsonProcessingException {
        String content= "This is the file content";
        prepareData(fileName, content);
    }

    @And("the response should show the original history")
    public void theResponseShouldShowTheOriginalFilenameList() {
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        String [] currentHistory = jsonPath.get("fileHistory");

        assertEquals(Arrays.toString(currentHistory), Arrays.toString(fileHistory));
        logger.debug(()-> "File history content: " + Arrays.toString(fileHistory));
    }


    @And("the file history is [{string}]")
    public void theFileHistoryIs_1(String fileName1) {
        pushFromLeft(this.fileHistory, fileName1);
        logger.debug(()->"File history content: " + Arrays.toString(fileHistory));
    }

    @And("the file history is [{string}, {string}]")
    public void theFileHistoryIs_2(String fileName1, String fileName2) {
        pushFromLeft(this.fileHistory, fileName2, fileName1);
        logger.debug(()->"File history content: " + Arrays.toString(fileHistory));
    }

    @And("the file history is [{string}, {string}, {string}]")
    public void theFileHistoryIs_3(String fileName1,  String fileName2, String fileName3) {
        pushFromLeft(this.fileHistory, fileName3, fileName2, fileName1);
        logger.debug(()->"File history content: " + Arrays.toString(fileHistory));
    }

    @Then("the request data should include:")
    public void theRequestDataShouldInclude(DataTable dataTable) throws JsonProcessingException {
        logger.info(()->"Validating the response content");
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Map<String, String> expectedValues = dataTable.asMap(String.class, String.class);

        for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
            String key = entry.getKey();
            var value = entry.getValue();

            switch (key) {
                case "history" -> {
                    String[] history = jsonPath.get("history");
                    assertEquals("File history is not matching!",Arrays.toString(value.toCharArray()), Arrays.toString(history));
                    logger.debug(()->"File history content: " + Arrays.toString(history));
                }
                case "content" -> {
                    String content = jsonPath.get(key);
                    assertEquals("Content is not matching!", fileContent, content);
                    logger.debug(()->"File content: "+content);
                }
                case "fileName" -> {
                    String fileName = jsonPath.get(key);
                    assertEquals("FileName is not matching!",fileName, this.fileName);
                    logger.debug(()->"File name: "+fileName);
                }
                default -> {
                    logger.info(()->"Unrecognized key: " + key);
                }
            }
        }

    }



}
