package steps.aams.settings.pages;

import engine.Driver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;
import pages.aams.settings.roles.Roles;

public class CreateEditPageCommonActions {
    private final WebDriver driver;
    private final Roles roles;
    private final CreateEditPagesPage createEditPagesPage;
    private final PageModelBase baseAction;

    public CreateEditPageCommonActions(Driver driver) {
        this.driver = driver.get();
        this.roles = new Roles(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditPagesPage = new CreateEditPagesPage(this.driver);
    }

    @Then("I go back to pages")
    public void goBackToPages(){
        // Click the View Pages button (To go back to the Pages)
        baseAction.clickButton(createEditPagesPage.viewPagesButton);
    }

    @Then("I should see a message in create/edit page")
    public void shouldSeeAMessage(String expectedMessage){
        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPagesPage.modalBody, createEditPagesPage.modalMessage.get(0),
                createEditPagesPage.modalCloseButton, expectedMessage);
    }
}
