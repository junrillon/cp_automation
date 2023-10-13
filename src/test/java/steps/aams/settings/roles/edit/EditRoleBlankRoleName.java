package steps.aams.settings.roles.edit;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

public class EditRoleBlankRoleName {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String desc;
    public static String status;

    public EditRoleBlankRoleName(Driver driver) {
        this.driver = driver.get();
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @Then("I clear the role name field blank in edit page")
    public void clearRoleNameBlankInEditPage() {
        // Clear the role name field
        baseAction.clearInputField(createEditRolePage.roleNameInput);
    }

    @Then("I submit the role editing form with blank role name")
    public void submitRoleEditingWithBlankRoleName(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);
    }
}
