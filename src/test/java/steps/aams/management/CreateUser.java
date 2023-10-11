package steps.aams.management;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.aams.Navigation;
import pages.aams.Users;
import java.util.List;

public class CreateUser {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Users users;
    private final pages.aams.Users createUser;

    public static String userEmail;
    public static String role;

    public CreateUser(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.users = new Users(this.driver);
        this.createUser = new pages.aams.Users(this.driver);
    }

    @When("I navigate to users")
    public void navigateToUsers(){
        // Navigate to users
        navigation.navigateToUsers();
    }

    @When("I create a user")
    public void clickCreateUserButton(){
        // Click Create User button
        users.clickCreateUserButton();
    }

    @When("I set user details")
    public void setUserDetails(DataTable userDetails){
        //Get data table from feature file
        List<List<String>> data = userDetails.asLists(String.class);
        userEmail = data.get(1).get(0);
        role = data.get(1).get(1);

        // Input value in email input field
        createUser.enterEmailValue(createUser.emailField, userEmail);

        // Select a role
        createUser.selectRole(role);
    }

    @Then("I click cancel")
    public void clickCancelCreateUserButton(){
        // Click Create User button
        users.clickCancelCreateUserButton();
    }

}



