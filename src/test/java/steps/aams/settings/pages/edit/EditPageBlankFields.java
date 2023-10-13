package steps.aams.settings.pages.edit;

import engine.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

public class EditPageBlankFields {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public EditPageBlankFields(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @When("I clear the page details")
    public void setPageDetails(){
        // Input value in page name input field
        baseAction.clearInputField(createEditPages.pageNameInput);

        // Input value in page path input field
        baseAction.clearInputField(createEditPages.pagePathInput);

        // Select a status
        baseAction.deselectDropdownOption(createEditPages.editStatusDropdown);
    }

    @Then("I submit the edit page form with blank fields")
    public void clickSubmitForPageEditingWithBlankFields(){
        String expectedMessage = "Page name field is required.\nThe page path field is required.\nThe status field is required.";

        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonEdit);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPages.modalBody, createEditPages.modalMessage.get(0),
                createEditPages.modalCloseButton, expectedMessage);
    }
}
