package steps.aams.settings.roles.edit;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

public class EditRoleBlankRoleStatus {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String desc;

    public EditRoleBlankRoleStatus(Driver driver) {
        this.driver = driver.get();
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @Then("I clear the role status field blank in edit page")
    public void clearRoleStatusBlankInEditPage() {
        // Deselect Status
        baseAction.deselectDropdownOption(createEditRolePage.statusDropdown);
    }

    @Then("I submit the role editing form with blank role status")
    public void submitRoleEditingWithBlankRoleStatus(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);

    }
}
