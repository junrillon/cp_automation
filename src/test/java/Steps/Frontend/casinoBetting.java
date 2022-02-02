package Steps.Frontend;

import Base.BaseUtil;
import Pages.Frontend.LoginPage;
import com.opencsv.CSVReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
    public void iClickTheLoginButton() throws InterruptedException {
        LoginPage page = new LoginPage(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

        //Get captcha and input captcha
        page.getAndInputCaptcha();

        //Click login button
        page.clickLoginBtn();

        Thread.sleep(1500);

        int activeSessionAlert = base.Driver.findElements(By.xpath("//div[@class='react-confirm-alert-body']")).size();
        if(activeSessionAlert == 1){
            WebElement activeSessionContinuebtn = page.activeSessionContinuebtn;
            wait.until(ExpectedConditions.elementToBeClickable(activeSessionContinuebtn));
            activeSessionContinuebtn.click();

            //for 2nd active session alert
            Thread.sleep(1000);
            int activeSessionAlert2 = base.Driver.findElements(By.xpath("//div[@class='react-confirm-alert-body']")).size();
            if(activeSessionAlert2 == 1) {
                wait.until(ExpectedConditions.visibilityOf(activeSessionContinuebtn));
                activeSessionContinuebtn.click();
            }
        }
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

        int int_stake = 0;
        int int_winnings = 0;
        int int_actualBalanceBeforeBet = 0;
        int int_actualBalanceAfterBet = 0;
        int int_expectedBalanceAfterBet = 0;
        int int_expectedBalanceAfterWin = 0;

        String separator = ","; //<-- will use it in saving data in csv file
        String gameLaunch_status = "";
        String betStatus = "";
        String settlementStatus = "";
        String string_winnings = "";
        String string_balanceAfter = "";

        String actualBalanceBeforeBet = "";
        String actualBalanceAfterBet = "";
        String stakeValue = "";




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
            longwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]")));
            WebElement gameCard = base.Driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]"));

            //Locate the position of the gameId
            int position = base.Driver.findElements(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']/preceding::div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3']")).size() + 1;

            //Creating object of an Actions class2, for the located gameId
            longwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]")));
            WebElement gameCard2 = base.Driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]"));

            //Performing the mouse hover action on the target element.
            boolean gameCard_isPresent = gameCard2.isDisplayed();
            if (gameCard_isPresent) {
                wait.until(ExpectedConditions.visibilityOf(gameCard2));
                action.moveToElement(gameCard2).perform();
            }

            WebElement extracted_gameId = base.Driver.findElement(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']"));
            boolean extracted_gameId_isPresent = extracted_gameId.isDisplayed();
            if (extracted_gameId_isPresent) {
                wait.until(ExpectedConditions.elementToBeClickable(extracted_gameId));
                extracted_gameId.click();
            }//section[//h1[@id='section-name']]
            //div[@class='header-left']/div[contains(text(),'Sprint 45')]/following-sibling::div[@class='ghx-issue-count']/preceding::div[@class='header-left]
            //div[//div[@class='ghx-meta plan-inline-create-inside']]
           //./ancestor-or-self::[@class="box"]
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

            catch (TimeoutException e)
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
                    boolean loading_isPresent = loadingScreen.isDisplayed();
                    while(loading_isPresent){
                        loading_isPresent = loadingScreen.isDisplayed();

                        if(!loading_isPresent){
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

                Thread.sleep(3000);
                int playButton_isPresent = base.Driver.findElements(By.xpath("//div[@class='play-button-transition']/button")).size();
                if(playButton_isPresent == 1){
                    wait.until(ExpectedConditions.elementToBeClickable(playButton));
                    playButton.click();

                } else {
                    WebElement gameScreen = base.Driver.findElement(By.xpath("//canvas[@id='stage']"));
                    wait.until(ExpectedConditions.visibilityOf(gameScreen));

                    int gameScreen_isPresent = base.Driver.findElements(By.xpath("//canvas[@id='stage']")).size();

                    var canvas_dimensions = gameScreen.getSize();
                    var canvas_center_x = canvas_dimensions.getWidth() / 2;
                    var canvas_center_y = canvas_dimensions.getHeight() / 2;
                    int button_x = (canvas_center_x / 14);
                    int button_y = (canvas_center_y / 11) * 8;

                    action.moveToElement(gameScreen, button_x, button_y).perform();
                    new Actions(base.Driver)
                            .moveToElement(gameScreen, button_x, button_y)
                            .click()
                            .perform();
                }

                Thread.sleep(1500);
                int overlay_message = base.Driver.findElements(By.xpath("//div[@class='v-dialog__content v-dialog__content--active']//div[@class='v-card__text dark-overlay message-contents']")).size();
                if(overlay_message == 1){
                    WebElement overlay_exitButton = base.Driver.findElement(By.xpath("//button[@class='v-btn v-btn--icon theme--dark']"));
                    wait.until(ExpectedConditions.elementToBeClickable(overlay_exitButton));
                    overlay_exitButton.click();
                }

                //WebElements for inner play button, balance display and win indicator
                WebElement innerPlayButton = page.innerPlayButton;
                WebElement balanceDisplay = page.balanceValue;

                //Wait for balance to display first before getting the value of the balance
                longwait.until(ExpectedConditions.visibilityOf(balanceDisplay));

                boolean balanceValue_isPresent = balanceDisplay.isDisplayed();
                if(balanceValue_isPresent){
                    wait.until(ExpectedConditions.visibilityOf(balanceDisplay));
                    wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
                    innerPlayButton.click();
                }

                actualBalanceBeforeBet = page.balanceValue.getText(); //Get balance before betting
                stakeValue = page.stakeValue.getText(); //Get the stake amount

                System.out.println("----------------- Start betting: " + gameName);
                System.out.println("Balance before bet: " + actualBalanceBeforeBet);

                //Remove the strings inside the balance
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace("PHP","");
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace(",","");
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace(".00","");

                //Remove the strings inside the stake
                stakeValue = stakeValue.replace("PHP","");
                stakeValue = stakeValue.replace(",","");
                stakeValue = stakeValue.replace(".00","");

                Thread.sleep(10000);

                //Get balance after betting and remove the word "PHP"
                actualBalanceAfterBet = page.balanceValue.getText();
                actualBalanceAfterBet = actualBalanceAfterBet.replace("PHP","");
                actualBalanceAfterBet = actualBalanceAfterBet.replace(",","");
                actualBalanceAfterBet = actualBalanceAfterBet.replace(".00","");

                //Start of computation
                int_stake = Integer.parseInt(stakeValue);
                int_actualBalanceBeforeBet = Integer.parseInt(actualBalanceBeforeBet);
                int_actualBalanceAfterBet = Integer.parseInt(actualBalanceAfterBet);
                int_expectedBalanceAfterBet = int_actualBalanceBeforeBet - int_stake;
                int_winnings = (int_actualBalanceAfterBet - int_actualBalanceBeforeBet);

                //start of conditions
                if(int_winnings == (int_stake * -1)){
                    System.out.println("Stake: " + int_stake + " | Winnings: " + int_winnings);

                    if(int_expectedBalanceAfterBet == int_actualBalanceAfterBet){
                        betStatus = "OK";
                    } else {
                        betStatus = "NOT OKAY";
                    }
                    System.out.println("Actual Balance: " + int_actualBalanceAfterBet + " | Expected Balance: " + int_expectedBalanceAfterBet);

                } else {
                    System.out.println("Stake: " + int_stake + " | Winnings: " + int_winnings);
                    int_expectedBalanceAfterWin = int_actualBalanceBeforeBet + int_winnings;
                    if(int_expectedBalanceAfterBet == int_actualBalanceAfterBet){
                        settlementStatus = "OK";
                    } else {
                        settlementStatus = "NOT OKAY";
                    }
                    System.out.println("Actual Balance: " + int_actualBalanceAfterBet + " | Expected Balance: " + int_expectedBalanceAfterWin);

                }

            }

            File location = new File("D:\\newfile.csv");
            FileWriter fr = new FileWriter(location, true);
            BufferedWriter br = new BufferedWriter(fr);

            //--- Input data in csv
            br.append(gameName);
            br.append(separator).append(gameLaunch_status);
            br.append(separator).append(betStatus);
            br.append(separator).append(String.valueOf(int_actualBalanceBeforeBet));
            br.append(separator).append(String.valueOf(int_stake));
            br.append(separator).append(String.valueOf(int_actualBalanceAfterBet));
            br.append(separator).append(String.valueOf(int_winnings));
            br.newLine();
            br.flush();

            Thread.sleep(1000);
            base.Driver.close();

            Thread.sleep(500);
            base.Driver.switchTo().window(oldTab);

       }

    }
}




