package steps.aams.settings.roles;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;
import pages.aams.settings.roles.Roles;

public class CreateEditRoleCommonActions {
    private final WebDriver driver;
    private final Roles roles;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public CreateEditRoleCommonActions(Driver driver) {
        this.driver = driver.get();
        this.roles = new Roles(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @Then("I go back to roles")
    public void goBackToRoles(){
        // Click the View Pages button (To go back to the Pages)
        baseAction.clickButton(createEditRolePage.viewRolesButton);
    }

    @Then("I should see a message in create/edit role")
    public void shouldSeeAMessage(String expectedMessage){
        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditRolePage.modalBody, createEditRolePage.modalMessage.get(0),
                createEditRolePage.modalCloseButton, expectedMessage);
    }
}
