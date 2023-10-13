package steps.aams.settings.roles.create;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

public class CreateRoleBlankFields {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public CreateRoleBlankFields(Driver driver) {
        this.driver = driver.get();
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I submit the role creation form with blank fields")
    public void submitRoleCreationWithBlankFields(){
        String expectedMessage = "Role name field is required.\nThe description field is required.\nThe status field is required.";

        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditRolePage.modalBody, createEditRolePage.modalMessage.get(0),
                                        createEditRolePage.modalCloseButton, expectedMessage);
    }
}
