package steps.aams.settings.pages.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class CreatePageExistingPageName {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public static String pageName;
    public static String status;
    public static String pagePath;

    public CreatePageExistingPageName(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I enter existing page name")
    public void enterExistingPageName(DataTable pageDetails) {
        String randomIntString = getRandomIntString(3);

        // Get the details in the Cucumber/Feature file
        List<List<String>> data = pageDetails.asLists(String.class);
        pageName = data.get(1).get(0);
        pagePath = data.get(1).get(1) + "-" + randomIntString;
        status = data.get(1).get(2);

        // Input value in page name
        baseAction.enterValue(createEditPages.pageNameInput, pageName);

        // Input value in page path
        baseAction.enterValue(createEditPages.pagePathInput, pagePath);

        // Select Status
        baseAction.selectDropdownOption(createEditPages.statusDropdown, status);

    }

    @Then("I submit the page creation form with existing page name")
    public void submitPageCreationWithExistingPageName(){
        String expectedMessage = "Page name has already been taken.";

        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonCreate);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPages.modalBody, createEditPages.modalMessage.get(0),
                createEditPages.modalCloseButton, expectedMessage);
    }
}
