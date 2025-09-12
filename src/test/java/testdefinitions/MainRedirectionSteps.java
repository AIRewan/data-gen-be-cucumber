package testdefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MainRedirectionSteps {
    @Then("the backend should store the file {string} and its content")
    public void theBackendShouldStoreTheFileAndItsContent(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the backend should respond with a {int} status code and redirect to the {string} page")
    public void theBackendShouldRespondWithAStatusCodeAndRedirectToThePage(int arg0, String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("the user has previously uploaded and validated a file named {string}")
    public void theUserHasPreviouslyUploadedAndValidatedAFileNamed(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user visits the main page")
    public void theUserVisitsTheMainPage() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the user presses the {string} button")
    public void theUserPressesTheButton(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the backend should retrieve {string} from local storage")
    public void theBackendShouldRetrieveFromLocalStorage(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the backend should respond with a {int} status code and redirect to the {string} page with the file content")
    public void theBackendShouldRespondWithAStatusCodeAndRedirectToThePageWithTheFileContent(int arg0, String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
