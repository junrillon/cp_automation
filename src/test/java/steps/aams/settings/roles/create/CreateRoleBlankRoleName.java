package steps.aams.settings.roles.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

import java.util.List;

public class CreateRoleBlankRoleName {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String desc;
    public static String status;

    public CreateRoleBlankRoleName(Driver driver) {
        this.driver = driver.get();
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the role name field blank")
    public void leaveRoleNameBlank(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        desc = data.get(1).get(0);
        status = data.get(1).get(1);

        // Input value in description field
        baseAction.enterValue(createEditRolePage.descriptionInput, desc);

        // Select Status
        baseAction.selectDropdownOption(createEditRolePage.statusDropdown, status);

    }

    @Then("I submit the role creation form with blank role name")
    public void submitRoleCreationWithBlankRoleName(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);
    }
}
