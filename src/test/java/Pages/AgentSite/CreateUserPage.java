package Pages.AgentSite;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class CreateUserPage {
    public CreateUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //Agent Username
    @FindBy(how = How.CSS, using =  "li.nav-item.dropdown.d-none.d-xl-block > #dropdown2")
    public WebElement agUsername;

    //Create User icon (click)
    @FindBy(how = How.CSS, using = "nav.main-navbar-top.navbar.navbar-expand-xl.navbar-dark.bg-dark.fixed-top > div.container-fluid > div#collapseLeft.collapse.navbar-collapse.main-navigation > ul.nav.navbar-nav > li.nav-item > a.nav-link.create-account")
    public WebElement createUserIcon;

    //Select Create Player
    @FindBy(how = How.CSS, using = "div.modal-content > div.modal-body.align-center > a.btn.grey-gallery")
    public WebElement createCustomer;

    //Select Referral Agents
    @FindBy(how = How.CSS, using = "select#masterAgentDropDown.form-control.select2")
    public WebElement selectMasterAgent;

    @FindBy(how = How.CSS, using = "select#subAgentDropDown.form-control.select2")
    public WebElement selectSubAgent;

    @FindBy(how = How.CSS, using = "select#agentDropDown.form-control.select2")
    public WebElement selectAgent;

    //Player's Credentials
    @FindBy (how = How.CSS, using = "input#agentUsernameInput.form-control")
    public  WebElement playerUsername;

    @FindBy (how = How.CSS, using = "input#passwordInput.form-control")
    public WebElement playerPassword;

    @FindBy (how = How.CSS, using = "input#confirmPasswordInput.form-control")
    public WebElement confirmPassword;

    //Copy to clipboard button
    @FindBy (how = How.CSS, using = "div.btn.btn-secondary.copyToClipboard")
    public WebElement copyUsername;

    //Transfer Funds upon account creation
    @FindBy (how = How.CSS, using = "input#amountToTransferInput.form-control")
    public WebElement fundTransfer;

    //Submit Player Creation form
   // @FindBy (how = How.CSS, using = "button#submitButton.btn.green-jungle")
   // public WebElement clickSubmit;

    //Navigate to Customer List
    @FindBy (how = How.CSS, using = "a.nav-link.dropdown-toggle")
    public WebElement usersTab;

    @FindBy (how = How.XPATH, using = "//*[@id=\"collapseLeft\"]/ul[1]/li[3]/ul/li[2]/a")
    public WebElement customerList;

    @FindBy (how = How.ID, using = "customer-username")
    public WebElement unameFilter;

    @FindBy (how = How.ID, using = "searchButton")
    public WebElement searchButton;

    @FindBy (how = How.XPATH, using = "//*[@id=\"playersTable\"]/tbody/tr/td[1]")
    public WebElement searchResult;


    /**
     * Object action
     */
    //Agent username is displayed
    public void agUsernameDisplay() {agUsername.isDisplayed();}

    //Click User Icon
    public void clickUserIcon() {createUserIcon.click();}

    //Click Create Customer
    public void clickCreateCustomer() {createCustomer.click();}

    //Select Referral Agents
    public void referralAgents() {
        selectMasterAgent.isDisplayed();
        Select selectMA = new Select(selectMasterAgent);
        selectMA.selectByIndex(2);

        selectSubAgent.isDisplayed();
        Select selectSB = new Select(selectSubAgent);
        selectSB.selectByIndex(1);

        selectAgent.isDisplayed();
        Select selectAG = new Select(selectAgent);
        selectAG.selectByIndex(1);
    }


    //Input Player Credentials
    public void inputPlayerCreds (String plUsername, String plPassword, String cfPassword) {

        plUsername = "pl";
        plPassword = "123123";
        cfPassword = "123123";

        int min = 25;
        int max = 200;
        int plnum = (int)Math.floor(Math.random()*(max-min+1)+min);


        playerUsername.sendKeys(plUsername + plnum);
        playerPassword.sendKeys(plPassword);
        confirmPassword.sendKeys(cfPassword);

    }

    //Copy player username to clipboard
    public void copyToClipboard() {copyUsername.click();}

    //Transfer Balance
    public void transferFund (String trFunds) {
        fundTransfer.sendKeys(trFunds);
    }

    //Click Submit button
    //public void clickSubmit () {clickSubmit.click();}

    //Customer List
    public void customerListPage () throws IOException, UnsupportedFlavorException {
        usersTab.click();
        customerList.click();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String plUsername = (String) clipboard.getData(DataFlavor.stringFlavor);

        unameFilter.sendKeys(plUsername);
        searchButton.click();
    }
}
