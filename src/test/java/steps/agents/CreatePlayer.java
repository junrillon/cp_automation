package steps.agents;

import com.opencsv.CSVWriter;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.agents.AgentDashboard;
import pages.agents.CreateCustomer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatePlayer {

    private final WebDriver driver;
    private final AgentDashboard agentDashboard;
    private final CreateCustomer createCustomer;
    private final PageModelBase pageModelBase;

    public CreatePlayer (Driver driver){
        this.driver = driver.get();
        this.agentDashboard = new AgentDashboard(this.driver);
        this.createCustomer = new CreateCustomer(this.driver);
        this.pageModelBase = new PageModelBase(this.driver);
    }

    @When("I click the create account")
    public void clickCreateAccount(){
        agentDashboard.clickCreateAccount();
    }

    @Then("I click the create customer")
    public void clickCreateCustomer(){
        agentDashboard.clickCreateCustomer();

    }

    List<String> usernameList = new ArrayList<>();
    @Then("I create {int} users")
    public void createMultipleCustomers(int totalUsers, DataTable details) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        List<String> formattedUsernameLists = new ArrayList<>();
        List<List<String>> data = details.asLists(String.class);

        String prefix = data.get(1).get(0);
        String masterAgent = data.get(1).get(1);
        String subAgent = data.get(1).get(2);
        String agent = data.get(1).get(3);
        String amountToTransfer = data.get(1).get(4);

        for(int i = 1; i <= totalUsers; i++) {
            String username = prefix + i;
            String pw = "123123";

            //Check and Select a Master Agent
            createCustomer.selectAgent(createCustomer.masterAgentDropDown, masterAgent);

            //Check and Select a Sub Agent
            createCustomer.selectAgent(createCustomer.subAgentDropDown, subAgent);

            //Check and Select an Agent
            createCustomer.selectAgent(createCustomer.agentDropDown, agent);

            //set username and password
            createCustomer.setUsernamePassword(username, pw);

            //Check for errors in username input,
            // if there's an error, it will generate new username prefix and input it
            createCustomer.checkUsernameErrorMessage();

            //set amount to transfer
            createCustomer.setAmountToTransfer(amountToTransfer);

            //Declare the WebElement for the copiedUsernameContainer, and get the text
            WebElement copiedUsernameContainer = createCustomer.copiedUsernameContainer;
            String copiedUsername = createCustomer.clickCopyUsername(copiedUsernameContainer);

            //set name in additional info
            createCustomer.setAdditionalInfoName(copiedUsername);

            //add the copied username into the createdUsernames array
            formattedUsernameLists.add("\""+copiedUsername+"\"");
            usernameList.add(copiedUsername);

            //Click the submit button
            createCustomer.clickSubmitButton();

            //Wait for the status message and assert the status message.
            createCustomer.getStatusMessage(createCustomer.statusMessageContainer);

            //display all the created usernames
            System.out.println("usernameList:" + formattedUsernameLists);

            //refresh page
            driver.navigate().refresh();
        }

        System.out.println("Username List: " + formattedUsernameLists);
    }

    @Then("I save the usernames to csv")
    public void writeToCSV(DataTable location){
        List<String> usernames = usernameList;

        // Get the location from the Cucumber file
        List<List<String>> data = location.asLists(String.class);

        // Assign the location to the csvFile variable
        String csvFile = data.get(1).get(0);

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            for (String username : usernames) {
                String[] record = {username};
                writer.writeNext(record);
            }
            System.out.println("Usernames written to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
