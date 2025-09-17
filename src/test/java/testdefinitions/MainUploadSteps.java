package testdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
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

public class MainUploadSteps {
    private static final Logger logger = LoggerFactory.getLogger(MainUploadSteps.class);

    private final String baseUrl = ConfigManager.getProperty("base.url");
    private final String uploadEndpoint = ConfigManager.getProperty("upload.endpoint");

    private String fileName;
    private String fileContent;
    private String [] fileHistory;
    private Response response;

    private void prepareData(String fileName, String content, String[] fileHistory) throws JsonProcessingException {
        logger.info(()->"Preparing to upload a file with name: " + fileName);
        this.fileName = fileName;


        this.fileHistory = fileHistory;
        logger.debug(()->"File history content: " + Arrays.toString(fileHistory));

        Map<String, Object> jsonContent = new HashMap<>();
        jsonContent.put("filename", fileName);
        jsonContent.put("content", content);
        jsonContent.put("history", fileHistory);

        ObjectMapper objectMapper = new ObjectMapper();
        this.fileContent = objectMapper.writeValueAsString(jsonContent);

        logger.debug(()->"Generated file content JSON payload: " + fileContent);
    }

    @Given("a file named {string} with valid content is uploaded")
    public void aFileNamedWithValidContentIsUploaded(String fileName) throws JsonProcessingException {
        String content= "This is the file content";
        prepareData(fileName, content, fileHistory);
    }

    @When("the backend processes the request")
    public void theBackendProcessesTheFile() {
        logger.info(()-> "Sending POST request to backend - URL: " + baseUrl + uploadEndpoint);
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
        fileHistory = new String[] {fileName, "fileName2", "fileName3"};
        String content= "This is the file content";
        prepareData(fileName, content, fileHistory);
    }

    @And("the response should show the original filename list")
    public void theResponseShouldShowTheOriginalFilenameList() {
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        String [] currentHistory = jsonPath.get("fileHistory");

        assertEquals(Arrays.toString(currentHistory), Arrays.toString(fileHistory));
        logger.debug(()-> "File history content: " + Arrays.toString(fileHistory));
    }
}
