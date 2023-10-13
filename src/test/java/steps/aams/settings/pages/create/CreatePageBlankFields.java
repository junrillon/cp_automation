package steps.aams.settings.pages.create;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

public class CreatePageBlankFields {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public CreatePageBlankFields(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I submit the page creation form with blank fields")
    public void submitPageCreationWithBlankFields(){
        String expectedMessage = "Page name field is required.\nThe page path field is required.\nThe status field is required.";

        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonCreate);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPages.modalBody, createEditPages.modalMessage.get(0),
                createEditPages.modalCloseButton, expectedMessage);
    }
}
