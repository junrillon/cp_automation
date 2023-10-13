package steps.aams.settings.pages.edit;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class EditPageBlankPageName {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public static String pagePath;
    public static String status;

    public EditPageBlankPageName(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the page name field blank in edit page")
    public void leavePageNameBlankInEditPage(DataTable pageDetails) {
        String randomIntString = getRandomIntString(3);

        // Assign the current values to a String and concat randomIntString
        List<List<String>> data = pageDetails.asLists(String.class);
        pagePath = data.get(1).get(0) + "-" + randomIntString;
        status = data.get(1).get(1);

        // Clear the page name input field
        baseAction.clearInputField(createEditPages.pageNameInput);

        // Input value in page path
        baseAction.enterValue(createEditPages.pagePathInput, pagePath);

        // Select Status
        baseAction.selectDropdownOption(createEditPages.editStatusDropdown, status);

    }

    @Then("I submit the edit page form with blank page name")
    public void clickSubmitForPageEditingWithBlankPageName(){
        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonEdit);
    }
}
