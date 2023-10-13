package steps.aams.settings.pages.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class CreatePageBlankPagePath {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public static String pageName;
    public static String status;

    public CreatePageBlankPagePath(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the page path blank")
    public void leavePagePathBlank(DataTable roleDetails) {
        String randomIntString = getRandomIntString(3);

        // Assign the current values to a String and concat randomIntString
        List<List<String>> data = roleDetails.asLists(String.class);
        pageName = data.get(1).get(0)  + "-" + randomIntString;
        status = data.get(1).get(1);

        // Input value in page name
        baseAction.enterValue(createEditPages.pageNameInput, pageName);

        // Select Status
        baseAction.selectDropdownOption(createEditPages.statusDropdown, status);

    }
    @Then("I submit the page creation form with blank page path")
    public void submitPageCreationWithBlankPagePath(){
        String expectedMessage = "The page path field is required.";

        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonCreate);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPages.modalBody, createEditPages.modalMessage.get(0),
                createEditPages.modalCloseButton, expectedMessage);
    }
}
