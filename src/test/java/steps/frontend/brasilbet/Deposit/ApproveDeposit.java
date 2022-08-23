package steps.frontend.brasilbet.Deposit;

import database.DatabaseConnection;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApproveDeposit {
    private WebDriver driver;
    public pages.PageModelBase PageModelBase;


    public ApproveDeposit(PageModelBase PageModelBase, Driver driver) {
        this.driver = driver.get();
        this.PageModelBase = PageModelBase;

    }

    String referenceNo;
    String amount;
    String created_at;

    @Given("I get details in br_user_fund_transactions table")
    public void iGetDetails(DataTable brUserFund){

        //get br_user_id from steps/frontend/Login
        List<List<String>> details = brUserFund.asLists(String.class);
        String transId = PixDeposit.transId;  //details.get(1).get(0);
        String env = details.get(1).get(1);

        System.out.println(transId);

        try {
            //check report_casino if null stage_b2c
            String query = "SELECT trans_id, reference_no, amount, created_at FROM `"+env+"`.`br_user_fund_transactions` " +
                    "WHERE trans_id = '"+transId+"'";

            System.out.println(query);
                                            //execB2CDBQuery stage/prod; execProdBrasilQuery prod brasil
            ResultSet rs = DatabaseConnection.execB2CDBQuery(query);
            referenceNo = rs.getString("reference_no");
            amount = rs.getString("amount");
            created_at = rs.getString("created_at");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("reference_no: " + referenceNo +
                            "\namount: " + amount +
                            "\ncreated_at: " + created_at);

    }

    @When("I navigate to jwt ([^\"]*)$")
    public void iNavigateToJWT(String url){
        driver.get(url);

    }

    @When("I input deposit details")
    public void iInputDepositDetails() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        String payload =
                "{ \n" +
                "\"id\": \""+ referenceNo +"\", \n" +
                "\"value\": "+ amount +", \n" +
                "\"paid_value\":  "+ amount +", \n" +
                "\"paid_at\": \""+ created_at +"\",\n" +
                "\"status\": \"PAID\" \n" +
                "}";

        System.out.println(payload);

        //Scroll into payload text area
        WebElement payloadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='js-payload']")));
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, payloadElement);

        WebElement payloadTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(concat(' ',@class,' '), ' js-payload ')]//div[contains(concat(' ',@class,' '), ' CodeMirror-code ')]")));
        payloadTextArea.click();

        Actions action = new Actions(driver);

        //Select all
        action.keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys("")
                .build().perform();

        //Paste the payload
        action.sendKeys(payload)
                .build()
                .perform();
    }

    @When("I input signature ([^\"]*)$")
    public void iInputSignature(String signature) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement signatureInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@name='secret']")));
        signatureInput.click();

        Actions action = new Actions(driver);

        //Clear the field then paste the signature
        action.keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(signature)
                .build().perform();

    }

    String generatedToken;
    @Then("I get the generated token")
    public void iGetTheGeneratedToken() {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement text = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(".//div[contains(concat(' ',@class,' '), ' box-content ')]//div[contains(concat(' ',@class,' '), ' CodeMirror-code ')]")));
        generatedToken = text.getText();
        System.out.println(generatedToken);

    }

    @And("I process the deposit ([^\"]*)$")
    public void iProcessTheDeposit(String callBack) {

        try {
            URL url = new URL(callBack);

            Map<String, Object> params = new LinkedHashMap<>();
            params.put("bodyEncrypted", generatedToken);

            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String, Object> param : params.entrySet()){
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));

            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);

            int status = connection.getResponseCode();
            String errorMessage = connection.getResponseMessage();
            System.out.println(url + "\n" +status + ": " + errorMessage);

            String response = sb.toString();
            System.out.println(response);

            Assert.assertEquals("Payment transaction was successfully processed.", response);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

