package Steps.Frontend;

import Base.BaseUtil;
import Pages.Frontend.RegistrationPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Registration {

    private BaseUtil base;

    public Registration(BaseUtil base) {
        this.base = base;
    }


    @Given("^I access frontend registration page ([^\"]*)$")
    public void iAccessFrontendRegistrationPage(String feUrl){
        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);

    }

    @When("^I fill out the fields$")
    public void iFillOutTheFields(DataTable userDetails){
        RegistrationPage registrationPage = new RegistrationPage(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 20);

        List<List<String>> data = userDetails.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);
        String fullName = data.get(1).get(2);
        String email = data.get(1).get(3);
        String contactNo = data.get(1).get(4);

        WebElement usernameField = registrationPage.username;
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        boolean username_displayed = usernameField.isDisplayed();
        if(username_displayed){
            //input data in each field
            registrationPage.username.sendKeys(username);
            registrationPage.password.sendKeys(password);
            registrationPage.confirmPassword.sendKeys(password);
            registrationPage.fullName.sendKeys(fullName);
            registrationPage.email.sendKeys(email);
            registrationPage.contact.sendKeys(contactNo);

            //get the captcha and then input in captcha field
            String captcha = registrationPage.captchaValue.getText();
            registrationPage.captcha.sendKeys(captcha);

            //wait for the submit button to be clickable and then click
            wait.until(ExpectedConditions.elementToBeClickable(registrationPage.submitButton));
            registrationPage.submitButton.click();

            //wait for the success modal
            WebElement regSuccessModal = registrationPage.registrationSuccessModal;
            wait.until(ExpectedConditions.visibilityOf(registrationPage.registrationSuccessModal));
            boolean regSuccessModal_displayed = regSuccessModal.isDisplayed();
            if(regSuccessModal_displayed){
                String successMsg = registrationPage.successMsg.getText();
                System.out.println(successMsg);

                //wait for the login button to be clickable and then click
                wait.until(ExpectedConditions.elementToBeClickable(registrationPage.loginBtnAfterRegistration));
                registrationPage.loginBtnAfterRegistration.click();
            }
        }
    }



    @Then("^I input username ([^\"]*) and password ([^\"]*) $")
    public void iLoginTheAccount(String username, String password){


    }
}