package steps.aams.management;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.PageModelBase;
import pages.aams.Navigation;
import pages.aams.management.users.Users;
import java.util.List;

public class CreateUser {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Users users;
    private final PageModelBase baseAction;

    public static String userEmail;
    public static String role;

    public CreateUser(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.users = new Users(this.driver);
    }

    @When("I navigate to users")
    public void navigateToUsers(){
        // Navigate to users
        navigation.navigateToUsers();
    }

    @When("I create a user")
    public void clickCreateUserButton(){
        // Click Create User button
        baseAction.clickButton(users.createUserButton);
    }

    @When("I set user details in bulk")
    public void setUserDetailsBulk(DataTable userDetails){
        //Get data table from feature file
        List<List<String>> data = userDetails.asLists(String.class);
        String usersFilePath = data.get(1).get(0);

        List<String[]> csvRows = baseAction.readDataFromCSV(usersFilePath);

        for (String[] row : csvRows) {
            String userEmail = row[0];
            String role = row[1];

            //userEmail = data.get(1).get(0);
            //role = data.get(1).get(1);

            // Input value in email input field
            baseAction.enterValue(users.emailField, userEmail);

            // Select a role
            baseAction.selectDropdownOption(users.roleOption, role);

            //Submit user creation
            baseAction.clickButton(users.submitCreateUserButton);

            //Close modal
            baseAction.clickButton(users.modalCloseButtonSubmit);

        }
    }

    @When("I set user details")
    public void setUserDetails(DataTable userDetails){
        //Get data table from feature file
        List<List<String>> data = userDetails.asLists(String.class);
            userEmail = data.get(1).get(0);
            role = data.get(1).get(1);

            // Input value in email input field
            baseAction.enterValue(users.emailField, userEmail);

            // Select a role
            baseAction.selectDropdownOption(users.roleOption, role);

            //Submit user creation
            baseAction.clickButton(users.submitCreateUserButton);

            //Close modal
            baseAction.clickButton(users.modalCloseButtonSubmit);

        }

    @Then("I click cancel")
    public void clickCancelCreateUserButton(){
        // Click Cancel Create User button
        baseAction.clickButton(users.cancelCreateUserButton);
    }

    @Then("I click submit user")
    public void clickSubmitCreateUserButton(){
        // Click Submit Create User button
        baseAction.clickButton(users.submitCreateUserButton);
    }

    @Then("I close success modal")
    public void clickCloseCreateUserModal(){
        // Click close modal for Create User button
        baseAction.clickButton(users.modalCloseButtonSubmit);
    }

    @Then("I check if user is created")
    public void checkCreatedUser(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Input value in page name input field

        baseAction.enterValue(users.emailFilter, userEmail);

        // Click the filter button
        baseAction.clickButton(users.filterButton);

        // Declare table element and wait for it to be visible.
        WebElement table = wait.until(ExpectedConditions.visibilityOf(users.table));
        int cellCount = users.getCellCount(table);
        String userNameCell;

        if (cellCount > 1) {
            String userRoleCell = users.getCellValue(table, 1);
            //String pageStatusCell = users.getCellValue(table, 2);

            Assert.assertEquals(userNameCell = users.getCellValue(table, 0), userEmail,
                    "Expected User Email: " + users + ", Actual User Email: " + userNameCell);

            //Assert.assertEquals(userRoleCell, users,
             //       "Expected User Role: " + users + ", Actual User Role: " + userRoleCell);

            Assert.assertTrue(userRoleCell.equalsIgnoreCase(role),
                    "Expected User Role: " + role + ",  Actual User Role: " + userRoleCell);
        } else {
            //Get the value of the cell and assert
            Assert.assertEquals(users.getCellValue(table, 0), userEmail,
                    "The " + userEmail + " is not found.");
        }
    }
}





