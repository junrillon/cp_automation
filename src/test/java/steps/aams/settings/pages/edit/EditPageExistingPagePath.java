package steps.aams.settings.pages.edit;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.pages.CreateEditPagesPage;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class EditPageExistingPagePath {
    private final WebDriver driver;
    private final CreateEditPagesPage createEditPages;
    private final PageModelBase baseAction;

    public static String pageName;
    public static String pagePath;
    public static String status;

    public EditPageExistingPagePath(Driver driver) {
        this.driver = driver.get();
        this.createEditPages = new CreateEditPagesPage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I enter existing page path in edit page")
    public void enterExistingPagePath(DataTable pageDetails) {
        String randomIntString = getRandomIntString(3);

        // Get the details in the Cucumber/Feature file
        List<List<String>> data = pageDetails.asLists(String.class);
        pageName = data.get(1).get(0) + " " + randomIntString;
        pagePath = data.get(1).get(1);
        status = data.get(1).get(2);

        // Input value in page name
        baseAction.enterValue(createEditPages.pageNameInput, pageName);

        // Input value in page path
        baseAction.enterValue(createEditPages.pagePathInput, pagePath);

        // Select Status
        baseAction.selectDropdownOption(createEditPages.editStatusDropdown, status);

    }

    @Then("I submit the edit page form with existing page path")
    public void submitPageCreationWithExistingPagePath() {
        // Click submit button
        baseAction.clickButton(createEditPages.submitButtonEdit);
    }
}
