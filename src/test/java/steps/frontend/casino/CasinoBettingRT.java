package steps.frontend.casino;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.LoginGGplay;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class CasinoBettingRT {
    private final WebDriver driver;

    public CasinoBettingRT(Driver driver) {
        this.driver = driver.get();
    }

    @Given("I navigate to games casino")
    public void iNavigateToGamesCasino() {
        LoginGGplay page = new LoginGGplay(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement gamesCasino = page.navGamesCasino;
        wait.until(ExpectedConditions.visibilityOf(gamesCasino));
        wait.until(ExpectedConditions.elementToBeClickable(gamesCasino));
        gamesCasino.click();

    }

    @When("I select provider")
    public void iSelectProvider(DataTable providerDetails) throws InterruptedException {
        LoginGGplay page = new LoginGGplay(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        List<List<String>> sportsDetails = providerDetails.asLists(String.class);
        String provider = sportsDetails.get(1).get(0);

        //Navigate to games casino
        page.locateProviderFilter();

        wait.until(ExpectedConditions.visibilityOf(page.gameCard));

        WebElement providerFilter = driver.findElement(By.xpath(".//label[contains(text(), '"+provider+"')]"));
        wait.until(ExpectedConditions.visibilityOf(providerFilter));
        providerFilter.click();

        WebElement rad = driver.findElement(By.xpath(".//label[contains(text(), '"+provider+"')]//preceding-sibling::input[@type='checkbox']"));
        wait.until(ExpectedConditions.elementToBeSelected(rad));
    }

    @When("I wait for casino games to load")
    public void iWaitCasinoGamesToLoad(){
        LoginGGplay page = new LoginGGplay(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][2]//div[@class='game-card']")));
        WebElement i = driver.findElement(By.xpath(".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][2]//div[@class='game-card']//img"));

        int h = i.getSize().height;
        while(h < 100) {
            h = i.getSize().height;

            if (h > 100) {
                System.out.println("Game image loaded.");
                break;
            }
        }
    }

    @When("I play casino game")
    public void playAvailableGames() throws IOException, InterruptedException, CsvValidationException {
        LoginGGplay page = new LoginGGplay(driver);
        Actions action = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebDriverWait longwait = new WebDriverWait(driver, 20);

        String oldTab = driver.getWindowHandle();
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
            WebElement gameCard = driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]"));

            //Locate the position of the gameId
            int position = driver.findElements(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']/preceding::div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3']")).size() + 1;

            //Creating object of an Actions class2, for the located gameId
            longwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]")));
            WebElement gameCard2 = driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+position+"]"));


            //Performing the mouse hover action on the target element.
            boolean gameCard_isPresent = gameCard2.isDisplayed();
            if (gameCard_isPresent) {
                wait.until(ExpectedConditions.visibilityOf(gameCard2));
                action.moveToElement(gameCard2).perform();
            }

            WebElement extracted_gameId = driver.findElement(By.xpath("//a[@href='/launch-game/"+gameId+"/play?scc=1']"));
            boolean extracted_gameId_isPresent = extracted_gameId.isDisplayed();
            if (extracted_gameId_isPresent) {
                wait.until(ExpectedConditions.elementToBeClickable(extracted_gameId));
                extracted_gameId.click();
                System.out.println("is_present: " + extracted_gameId_isPresent);
            }//section[//h1[@id='section-name']]
            //div[@class='header-left']/div[contains(text(),'Sprint 45')]/following-sibling::div[@class='ghx-issue-count']/preceding::div[@class='header-left]
            //div[//div[@class='ghx-meta plan-inline-create-inside']]
            //./ancestor-or-self::[@class="box"]
            //Switch to game launch tab
            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }
            System.out.println("Switch");
            System.out.println("Switch 1");

            String checkIfMaintenance = page.maintenanceNotif.getText();
            //System.out.println(checkIfMaintenance);

            if(!checkIfMaintenance.contains("Game under maintenance")){
                //WebElement for iframe and switch focus to iframe
                WebElement iframe = page.gameIframe;

                longwait.until(ExpectedConditions.visibilityOf(iframe));
                boolean iframe1_isPresent = iframe.isDisplayed();
                if (iframe1_isPresent) {
                    longwait.until(ExpectedConditions.visibilityOf(iframe));
                    driver.switchTo().frame(iframe);
                    System.out.println("Done switching to iframe1");

                    //Thread.sleep(1500);
                    WebElement loadingScreen = driver.findElement(By.xpath("//div[@class='loading-screen']"));
                    boolean loading_isPresent = loadingScreen.isDisplayed();
                    while(loading_isPresent){
                        loading_isPresent = loadingScreen.isDisplayed();

                        if(!loading_isPresent){
                            gameLaunch_status = "OK";
                            break;
                        }
                    }
                }

                //WebElement for iframe2 and switch focus to iframe
                longwait.until(ExpectedConditions.visibilityOf(page.gameIframe2));
                driver.switchTo().frame(page.gameIframe2);
                System.out.println("Done switching to iframe2");

                //WebElement for play button and will wait for 20 secs
                WebElement playButton = page.playButton;

                //Thread.sleep(3000);
                int playButton_isPresent = driver.findElements(By.xpath("//div[@class='play-button-transition']/button")).size();
                if(playButton_isPresent == 1){
                    wait.until(ExpectedConditions.elementToBeClickable(playButton));
                    playButton.click();
                    System.out.println("playButton_isPresent: " + playButton_isPresent);

                } else {
                    WebElement gameScreen = driver.findElement(By.xpath("//canvas[@id='stage']"));
                    wait.until(ExpectedConditions.visibilityOf(gameScreen));

                    int gameScreen_isPresent = driver.findElements(By.xpath("//canvas[@id='stage']")).size();

                    var canvas_dimensions = gameScreen.getSize();
                    var canvas_center_x = canvas_dimensions.getWidth() / 2;
                    var canvas_center_y = canvas_dimensions.getHeight() / 2;
                    int button_x = (canvas_center_x / 14);
                    int button_y = (canvas_center_y / 11) * 8;

                    action.moveToElement(gameScreen, button_x, button_y).perform();
                    new Actions(driver)
                            .moveToElement(gameScreen, button_x, button_y)
                            .click()
                            .perform();
                }

                int overlay_message = driver.findElements(By.xpath(".//div[@class='v-dialog__content v-dialog__content--active']//div[@class='v-card__text dark-overlay message-contents']")).size();
                if(overlay_message == 1){
                    WebElement overlay_exitButton = driver.findElement(By.xpath(".//button[@class='v-btn v-btn--icon theme--dark']"));
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

//            Thread.sleep(1000);
//            driver.close();
//
//            Thread.sleep(500);
//            driver.switchTo().window(oldTab);

        }

        }
    }
