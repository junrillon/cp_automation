package steps.aams.settings.roles.edit;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

public class EditRoleBlankRoleDesc {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String status;

    public EditRoleBlankRoleDesc(Driver driver) {
        this.driver = driver.get();
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @Then("I clear the description field blank in edit page")
    public void clearRoleDescBlankInEditPage() {
        // Clear the role desc field
        baseAction.clearInputField(createEditRolePage.descriptionInput);
    }

    @Then("I submit the role editing form with blank role desc")
    public void submitRoleEditingWithBlankRoleDescription(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);
    }
}
