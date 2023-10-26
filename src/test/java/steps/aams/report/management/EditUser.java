package steps.aams.report.management;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.aams.Navigation;
import pages.aams.management.users.Users;

import java.util.List;

public class EditUser {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Users users;
    private final PageModelBase baseAction;

    public static String userEmail;
    public static String role;
    public static String status;

    public EditUser(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.users = new Users(this.driver);
    }

    @When("I navigate to users to edit")
    public void editNavigateToUsers(){
        // Navigate to users
        navigation.navigateToUsers();
    }

    @Then("I search for a user")
    public void searchCreatedUser(DataTable userDetails){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = userDetails.asLists(String.class);
        userEmail = data.get(1).get(0);

        // Input value in page name input field
        //users.enterEmailValue(users.emailFilter, userEmail);
        baseAction.enterValue(users.emailFilter, userEmail);

        // Click the filter button
        baseAction.clickButton(users.filterButton);

        // Declare table element and wait for it to be visible.
        /*WebElement table = wait.until(ExpectedConditions.visibilityOf(users.table));
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
        }*/
    }

    @When("I edit the user")
    public void clickEditUserButton(){
        // Click Edit User button
        baseAction.clickButton(users.editUserButton);
    }

    @Then("I edit the user details")
    public void editUserDetails(DataTable editUserDetails){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = editUserDetails.asLists(String.class);
        role = data.get(1).get(0);
        status = data.get(1).get(1);

        //Set data for user edit
        baseAction.selectDropdownOption(users.roleOption, role);
        baseAction.selectDropdownOption(users.statusOption, status);

    }

    @Then("I click submit edit user")
    public void clickSubmitEditUserButton(){
        // Click Submit Edit User button
        baseAction.clickButton(users.submitEditUserButton);
    }

    @Then("I close edit success modal")
    public void clickCloseEditUserModal(){
        // Click Close Modal for Edit User
        baseAction.clickButton(users.modalCloseButtonSubmit);
    }


}
