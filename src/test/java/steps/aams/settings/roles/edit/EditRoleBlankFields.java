package steps.aams.settings.roles.edit;

import engine.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;
import pages.aams.settings.roles.Roles;

public class EditRoleBlankFields {
    private final WebDriver driver;
    private final Roles roles;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String desc;
    public static String status;

    public EditRoleBlankFields(Driver driver) {
        this.driver = driver.get();
        this.roles = new Roles(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @When("I clear the role details in edit page")
    public void clearRoleDetailsInEditPage(){
        // Input value in page name input field
        baseAction.clearInputField(createEditRolePage.roleNameInput);

        // Input value in page path input field
        baseAction.clearInputField(createEditRolePage.descriptionInput);

        // Select a status
        baseAction.deselectDropdownOption(createEditRolePage.statusDropdown);
    }

    @When("I submit the empty role editing form")
    public void submitEmptyRoleEditingForm(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);

    }
}
