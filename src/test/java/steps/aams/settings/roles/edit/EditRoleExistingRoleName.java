package steps.aams.settings.roles.edit;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

import java.util.List;

public class EditRoleExistingRoleName {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;

    public EditRoleExistingRoleName(Driver driver) {
        this.driver = driver.get();
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @Then("I enter existing role name in edit page")
    public void enterExistingRoleNameInEditPage(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        roleName = data.get(1).get(0);

        // Input value in description field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);
    }

    @Then("I submit the role editing form with existing role name")
    public void submitRoleEditingWithExistingRoleName(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);
    }
}
