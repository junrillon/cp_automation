package steps.aams.settings.roles.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

import java.util.List;

public class CreateRoleExistingRoleName {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String status;
    public static String desc;

    public CreateRoleExistingRoleName(Driver driver) {
        this.driver = driver.get();
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I enter existing role name")
    public void enterExistingRoleName(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        roleName = data.get(1).get(0);
        desc = data.get(1).get(1);
        status = data.get(1).get(2);

        // Input value in role name field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);

        // Input value in description field
        baseAction.enterValue(createEditRolePage.descriptionInput, desc);

        // Select Status
        baseAction.selectDropdownOption(createEditRolePage.statusDropdown, status);

    }

    @Then("I submit the role creation form with existing role name")
    public void submitRoleCreationWithExistingRoleName(){

        String expectedMessage = "Role name has already been taken.";

        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditRolePage.modalBody, createEditRolePage.modalMessage.get(0),
                                        createEditRolePage.modalCloseButton, expectedMessage);
    }
}
