package testdefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FormGenerationSteps {
    @When("the backend generates the JSON")
    public void theBackendGeneratesTheJSON() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the backend should generate the following JSON:")
    public void theBackendShouldGenerateTheFollowingJSON() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the backend should redirect the user to the {string} page with this JSON")
    public void theBackendShouldRedirectTheUserToThePageWithThisJSON(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
