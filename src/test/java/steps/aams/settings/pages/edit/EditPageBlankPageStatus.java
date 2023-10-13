package steps.aams.settings.pages.edit;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class EditPageBlankPageStatus {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public static String pageName;
    public static String pathPath;

    public EditPageBlankPageStatus(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the page status dropdown blank in edit page")
    public void leavePageStatusBlankInEditPage(DataTable pageDetails) {
        String randomIntString = getRandomIntString(3);

        // Assign the current values to a String and concat randomIntString
        List<List<String>> data = pageDetails.asLists(String.class);
        pageName = data.get(1).get(0) + "-" + randomIntString;
        pathPath = data.get(1).get(1) + "-" + randomIntString;

        // Input value in page name
        baseAction.enterValue(createEditPages.pageNameInput, pageName);

        // Input value in page path
        baseAction.enterValue(createEditPages.pagePathInput, pathPath);

        // Select a status
        baseAction.deselectDropdownOption(createEditPages.editStatusDropdown);

    }

    @Then("I submit the edit page form with blank page status")
    public void clickSubmitForPageEditingWithBlankPageStatus(){
        String expectedMessage = "The status field is required.";

        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonEdit);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPages.modalBody, createEditPages.modalMessage.get(0),
                createEditPages.modalCloseButton, expectedMessage);
    }
}
