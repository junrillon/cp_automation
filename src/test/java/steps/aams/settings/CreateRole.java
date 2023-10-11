package steps.aams.settings;

import engine.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.aams.Navigation;
import pages.aams.settings.page.Pages;
import pages.aams.settings.page.CreatePage;

public class CreateRole {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Pages pages;
    private final CreatePage createPage;

    public static String pageName;
    public static String pagePath;
    public static String status;

    public CreateRole(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.pages = new Pages(this.driver);
        this.createPage = new CreatePage(this.driver);
    }

    @When("I navigate to roles")
    public void navigateToRoles(){
        // Navigate to pages
        navigation.navigateToPages();
    }

    @When("I create a role")
    public void createPage(){
        // Click create page button
        pages.clickCreatePageButton();

    }
}
