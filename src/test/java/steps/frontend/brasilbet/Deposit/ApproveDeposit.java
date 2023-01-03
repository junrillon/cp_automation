package steps.frontend.brasilbet.Deposit;

import com.mysql.cj.log.Log;
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
import pages.frontend.GeneralDashboard;
import pages.frontend.brasilbet.Deposit;
import steps.frontend.Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApproveDeposit {
    private final WebDriver driver;
    public pages.PageModelBase PageModelBase;
    public steps.frontend.Login Login;
    public steps.frontend.Actions Actions;

    public ApproveDeposit(steps.frontend.Actions Actions, Driver driver) {
        this.driver = driver.get();
        this.Actions = Actions;

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
            //buft stands for br_user_fund_transactions
            ResultSet buft = DatabaseConnection.execB2CDBQuery(query);
            referenceNo = buft.getString("reference_no");
            amount = buft.getString("amount");
            created_at = buft.getString("created_at");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("reference_no: " + referenceNo +
                            "\namount: " + amount +
                            "\ncreated_at: " + created_at);

    }

    String deposit_rollover_multiplier;
    @Given("I get agent product details")
    public void iGetAgentProductDetails(){

        String wwwl_url = steps.frontend.Login.www_url;

        try {
            //check report_casino if null stage_b2c
            String query = " SELECT deposit_rollover_multiplier FROM `stage_b2c`.`agent_products` " +
                    "WHERE www_url = '"+wwwl_url+"'";

            System.out.println(query);

            //execB2CDBQuery stage/prod; execProdBrasilQuery prod brasil
            //ap stands for agent_products
            ResultSet ap = DatabaseConnection.execB2CDBQuery(query);
            deposit_rollover_multiplier = ap.getString("deposit_rollover_multiplier");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("deposit_rollover_multiplier: " + deposit_rollover_multiplier);

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

        WebElement payloadTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(".//div[contains(concat(' ',@class,' '), ' js-payload ')]//div[contains(concat(' ',@class,' '), ' CodeMirror-code ')]")));
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
                postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));

            }

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

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

    String depositAmount = PixDeposit.depositAmount;
    String balanceBeforeDeposit = PixDeposit.balanceBefore;
    String rolloverBeforeDeposit = PixDeposit.rolloverBefore;
    String balanceAfterDeposit;
    String rolloverAfterDeposit;

    @Then("I check the balance after deposit")
    public void checkTheBalance(){
        //Go to fundHistory
        Actions.goToFundHistory();

        //Get the balance and rollover
        balanceAfterDeposit = steps.frontend.Actions.getBalance();
        rolloverAfterDeposit = steps.frontend.Actions.getRollover();

        //Balance computation
        BigDecimal deposit_Amount = new BigDecimal(depositAmount);
        BigDecimal balanceBefore = new BigDecimal(balanceBeforeDeposit);

        BigDecimal balanceAfter = new BigDecimal(balanceAfterDeposit);
        BigDecimal expectedBalanceAfterDeposit = balanceBefore.add(deposit_Amount);

        //Rollover computation
        BigDecimal rolloverBefore = new BigDecimal(rolloverBeforeDeposit);
        BigDecimal rolloverAfter = new BigDecimal(rolloverAfterDeposit);
        BigDecimal rolloverMultiplier = new BigDecimal(deposit_rollover_multiplier);

        //deposit amount multiply by deposit_rollover_multiplier
        BigDecimal computedRollover = deposit_Amount.multiply(rolloverMultiplier);

        //computed rollover plus rollover before deposit
        BigDecimal expectedRolloverAfterDeposit = rolloverBefore.add(computedRollover);

        if(expectedBalanceAfterDeposit.equals(balanceAfter)  && (expectedRolloverAfterDeposit.equals(rolloverAfter))){
            Assert.assertEquals(expectedBalanceAfterDeposit, balanceAfter);
            System.out.println("Deposit amount: " + depositAmount +
                    "\nBalance before:" + balanceBeforeDeposit +
                    "\nBalance after: " + balanceAfterDeposit);

            Assert.assertEquals(expectedBalanceAfterDeposit, balanceAfter);
            System.out.println("The balance and rollover are both tally." +
                    "\nRollover multiplier: " + deposit_rollover_multiplier +
                    "\nComputed rollover: " + computedRollover +
                    "\nRollover before: " + rolloverBeforeDeposit +
                    "\nRollover after: " + rolloverAfterDeposit);

        } else {
            System.out.println("Either the balance or the rollover is not tally."+
                    "\nDeposit amount: " + depositAmount +
                    "\nBalance before:" + balanceBeforeDeposit +
                    "\nBalance after: " + balanceAfterDeposit +

                    "\nRollover multiplier: " + deposit_rollover_multiplier +
                    "\nComputed rollover: " + computedRollover +
                    "\nRollover before: " + rolloverBeforeDeposit +
                    "\nRollover after: " + rolloverAfterDeposit);
        }
    }
}

