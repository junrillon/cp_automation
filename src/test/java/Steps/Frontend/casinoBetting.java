package Steps.Frontend;

import Base.BaseUtil;
import Pages.Frontend.LoginPage;
import com.opencsv.CSVReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class casinoBetting {
    private BaseUtil base;

    public casinoBetting(BaseUtil base) {
        this.base = base;
    }


    @Given("^access frontend login page ([^\"]*)$")
    public boolean iCanAccessFrontendLoginPage(String feUrl) {
        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);

        try {

            //Click Banner Exit button
            WebDriverWait wait = new WebDriverWait(base.Driver, 2);
            WebElement bannerExitBtn;
            //  base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class = 'fa fa-times-circle fa-2x']")));
            bannerExitBtn.click();

        }

        catch (org.openqa.selenium.TimeoutException e)
        {
            return false;
        }

        return false;

    }

    @When("^input the Username ([^\"]*) and ([^\"]*)$")
    public void iInputTheUsernameAndPassword(String username, String password) {
        //Input username and password
        LoginPage page = new LoginPage(base.Driver);
        page.Login(username, password);


    }

    @And("click the login button to access the betting page")
    public void iClickTheLoginButton(){

        LoginPage page = new LoginPage(base.Driver);

        //Get captcha and input captcha
        page.getAndInputCaptcha();

        //Click login button
        page.clickLoginBtn();
    }

    @And("navigate to games casino")
    public void navigateToGamesCasino() {
        LoginPage page = new LoginPage(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

        WebElement gamesCasino = page.navGamesCasino;
        wait.until(ExpectedConditions.visibilityOf(gamesCasino));
        wait.until(ExpectedConditions.elementToBeClickable(gamesCasino));
        gamesCasino.click();

    }

    @And("select provider")
    public void selectProvider(DataTable providerDetails){
        LoginPage page = new LoginPage(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

        List<List<String>> sportsDetails = providerDetails.asLists(String.class);
        String provider = sportsDetails.get(1).get(0);

        //Navigate to games casino
        page.locateProviderFilter();

        WebElement gameCard = page.gameCard;
        wait.until(ExpectedConditions.visibilityOf(gameCard));

        WebElement providerFilter = base.Driver.findElement(By.xpath("//label[contains(text(), '"+provider+"')]"));
        wait.until(ExpectedConditions.visibilityOf(providerFilter));
        providerFilter.click();

        //casino games =
        //a[@href='/launch-game/"+4237+"/play?scc=1'] <--

    }

    @Then("play available games")
    public void playAvailableGames() throws IOException, InterruptedException {
        LoginPage page = new LoginPage(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
        WebDriverWait longwait = new WebDriverWait(base.Driver, 20);
        Actions action = new Actions(base.Driver);

        String oldTab = base.Driver.getWindowHandle();
        CSVReader reader = new CSVReader(new FileReader("D:\\ids.csv"));
        int rows = 0;
        String[] csvCell;

        int balanceBefore;
        int balanceAfter;
        int stake;
        int expectedBalanceAfterBet;

        String separator = ","; //<-- will use it in saving data in csv file
        String gameLaunch_status = "";
        String bet_status = "";
        String settlement_status = "";
        String string_winnings = "";
        String string_balanceAfter = "";

        String balanceValueBefore = "";
        String stakeValue = "";

        int winnings = 0;


        HashMap<String, String> updates = new HashMap<>();

       while ((csvCell = reader.readNext()) != null) {
            String gameId = csvCell[0];
            String gameName = csvCell[1];
            gameName = gameName.replace(","," ");
            rows++;

            //Check if the cell has a value, if empty end the testing
            if(gameId.isEmpty()){
                System.out.println("End of testing.");
                break;
            }

            //Creating object of an Actions class
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]")));
            WebElement gameCard = base.Driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]"));

            int position = base.Driver.findElements(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']/preceding::div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3']")).size() + 1;

            //Creating object of an Actions class2
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]")));
            WebElement gameCard2 = base.Driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]"));

            //Performing the mouse hover action on the target element.
            boolean gameCard_isPresent = gameCard2.isDisplayed();
            if (gameCard_isPresent) {
                wait.until(ExpectedConditions.visibilityOf(gameCard));
                action.moveToElement(gameCard2).perform();
            }

            WebElement extracted_gameId = base.Driver.findElement(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']"));
            boolean extracted_gameId_isPresent = extracted_gameId.isDisplayed();
            if (extracted_gameId_isPresent) {
                wait.until(ExpectedConditions.elementToBeClickable(extracted_gameId));
                extracted_gameId.click();
            }

            //Switch to game launch tab
            for(String winHandle : base.Driver.getWindowHandles()){
                base.Driver.switchTo().window(winHandle);
            }

            try {
                //Click Banner Exit button
                WebElement bannerExitBtn;
                bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class = 'fa fa-times-circle fa-2x']")));
                bannerExitBtn.click();
            }

            catch (org.openqa.selenium.TimeoutException e)
            {
                return;
            }

            Thread.sleep(1500);

            String checkIfMaintenance = page.maintenanceNotif.getText();
            //System.out.println(checkIfMaintenance);

            if(!checkIfMaintenance.contains("Game under maintenance")){
                //WebElement for iframe and switch focus to iframe
                WebElement iframe = page.gameIframe;

                longwait.until(ExpectedConditions.visibilityOf(iframe));
                boolean iframe1_isPresent = iframe.isDisplayed();
                if (iframe1_isPresent) {
                    longwait.until(ExpectedConditions.visibilityOf(iframe));
                    base.Driver.switchTo().frame(iframe);
                    //System.out.println("Done switching to iframe1");

                    Thread.sleep(1500);
                    WebElement loadingScreen = base.Driver.findElement(By.xpath("//div[@class='loading-screen']"));
                    //longwait.until(ExpectedConditions.invisibilityOf(loading));
                    boolean loading_isPresent = loadingScreen.isDisplayed();
                    while(loading_isPresent){
                        boolean loading_isStillPresent = loadingScreen.isDisplayed();

                        if(!loading_isStillPresent){
                           // System.out.println("Loading is done.");
                            gameLaunch_status = "OK";
                            break;
                        }
                    }
                }

                //WebElement for iframe and switch focus to iframe
                WebElement iframe2 = page.gameIframe2;
                longwait.until(ExpectedConditions.visibilityOf(iframe2));
                base.Driver.switchTo().frame(iframe2);

                //System.out.println("Done switching to iframe2");

                //WebElement for play button and will wait for 20 secs
                WebElement playButton = page.playButton;
                longwait.until(ExpectedConditions.visibilityOf(playButton));
                playButton.click();

                //WebElements for inner play button, balance display and win indicator
                WebElement innerPlayButton = page.innerPlayButton;
                WebElement balanceDisplay = page.balanceValue;
                WebElement winIndicator = page.winIndicator;

                //Wait for balance to display first before getting the value of the balance
                longwait.until(ExpectedConditions.visibilityOf(balanceDisplay));

                //Get balance before betting
                balanceValueBefore = page.balanceValue.getText();

                //Get the stake amount
                stakeValue = page.stakeValue.getText();

                boolean balanceValue_isPresent = balanceDisplay.isDisplayed();
                if(balanceValue_isPresent){
                    wait.until(ExpectedConditions.visibilityOf(balanceDisplay));
                    wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
                    innerPlayButton.click();

                    System.out.println("----------------- Start betting: " + gameName);
                    System.out.println("Balance before bet: " + balanceValueBefore);
                    System.out.println("Stake: " + stakeValue);
                }

                //Remove the strings inside the balance
                balanceValueBefore = balanceValueBefore.replace("PHP","");
                balanceValueBefore = balanceValueBefore.replace(",","");
                balanceValueBefore = balanceValueBefore.replace(".00","");

                //Remove the strings inside the stake
                stakeValue = stakeValue.replace("PHP","");
                stakeValue = stakeValue.replace(",","");
                stakeValue = stakeValue.replace(".00","");

                Thread.sleep(10000);

                //Get css value of win container element
                String winStatus = winIndicator.getCssValue("display");

                //Get balance after betting and remove the word "PHP"
                String balanceValueAfter = page.balanceValue.getText();
                balanceValueAfter = balanceValueAfter.replace("PHP","");
                balanceValueAfter = balanceValueAfter.replace(",","");
                balanceValueAfter = balanceValueAfter.replace(".00","");

                balanceBefore = Integer.parseInt(balanceValueBefore);
                stake = Integer.parseInt(stakeValue);
                balanceAfter = Integer.parseInt(balanceValueAfter);

                if(winStatus.equals("none")){
                    expectedBalanceAfterBet = balanceBefore - stake;
                    string_winnings = "0";
                    if(expectedBalanceAfterBet == balanceAfter){
                        bet_status = "OK";
                        string_balanceAfter = String.valueOf(expectedBalanceAfterBet);
                        System.out.println("Actual Balance: " + balanceAfter + " | Expected balance: " + expectedBalanceAfterBet);

                    } else {
                        bet_status = "NOT OKAY";
                        string_balanceAfter = String.valueOf(balanceAfter);
                        Assert.fail("Actual Balance: " + balanceAfter + " | Expected balance: " + expectedBalanceAfterBet);
                    }

                } else {

                    winnings = ((balanceBefore - balanceAfter) * -1);
                    string_winnings = String.valueOf(winnings);
                    System.out.println("Winnings: " + winnings +
                            "\nBalance after bet: "+ balanceAfter);

                    // Get entire page screenshot
                    File screenshot = page.winIndicator2.getScreenshotAs(OutputType.FILE);

                    // Copy the element screenshot to disk
                    File screenshotLocation = new File("D:\\Ss of winnings\\"+gameName+".png");
                    FileUtils.copyFile(screenshot, screenshotLocation);
                }

            }

            File location = new File("D:\\newfile.csv");
            FileWriter fr = new FileWriter(location, true);
            BufferedWriter br = new BufferedWriter(fr);

            //--- Input data in csv
            br.append(gameName);
            br.append(separator).append(gameLaunch_status);
            br.append(separator).append(bet_status);
            br.append(separator).append(balanceValueBefore);
            br.append(separator).append(stakeValue);
            br.append(separator).append(string_balanceAfter);
            br.append(separator).append(string_winnings);
            br.newLine();
            br.flush();

            Thread.sleep(1000);
            base.Driver.close();

            Thread.sleep(500);
            base.Driver.switchTo().window(oldTab);

       }

    }
}




