package steps.frontend.ggplay.casino;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.casino.CasinoGames;
import steps.GamesCasino;
import steps.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RTBetting {
    private final WebDriver driver;

    public RTBetting(Driver driver) {
        this.driver = driver.get();
    }

    @When("I play casino game")
    public void iPlayCasinoGame() throws InterruptedException {

        CasinoGames CasinoGames = new CasinoGames(driver);
        Actions action = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebDriverWait longwait = new WebDriverWait(driver, 60);

        String report;
        String betStatus;
        String balanceBeforeBetNoFormat;

        String actualBalanceBeforeBet;
        String actualBalanceAfterBet;
        String stakeValue;

        //Performing the mouse hover action on the target element.
        wait.until(ExpectedConditions.visibilityOf(CasinoGames.gameCard));
        action.moveToElement(CasinoGames.gameCard).perform();
        System.out.println("Hover at game card");

        //Wait for play button to be visible and clickable
        wait.until(ExpectedConditions.visibilityOf(CasinoGames.gameCardPlayButton));
        wait.until(ExpectedConditions.elementToBeClickable(CasinoGames.gameCardPlayButton));
        CasinoGames.gameCardPlayButton.click();
        System.out.println("Click play button");

        String checkIfMaintenance = CasinoGames.maintenanceNotif.getText();
        if(!checkIfMaintenance.contains("Game under maintenance")){
            //WebElement for iframe and switch focus to iframe
            WebElement iframe = CasinoGames.gameIframe1;

            longwait.until(ExpectedConditions.visibilityOf(iframe));
            boolean iframe1_isPresent = iframe.isDisplayed();
            if (iframe1_isPresent) {
                //Wait and switch focus to iframe1
                longwait.until(ExpectedConditions.visibilityOf(iframe));
                driver.switchTo().frame(iframe);
                System.out.println("Done switching to iframe1");

                //Check and wait for loading screen
                wait.until(ExpectedConditions.visibilityOf(CasinoGames.casinoLoadingScreen));
                boolean loading_isPresent = CasinoGames.casinoLoadingScreen.isDisplayed();
                while(loading_isPresent){
                    loading_isPresent = CasinoGames.casinoLoadingScreen.isDisplayed();

                    if(!loading_isPresent){
                        break;
                    }
                }
            }

            //Wait and switch focus to iframe2
            WebElement iframe2 = CasinoGames.gameIframe2;
            longwait.until(ExpectedConditions.visibilityOf(iframe2));
            driver.switchTo().frame(iframe2);
            System.out.println("Done switching to iframe2");

            //Close the debugger
            if(CasinoGames.debugComponent.size() > 0){
                wait.until(ExpectedConditions.visibilityOf(CasinoGames.debugComponent.get(0)));
                wait.until(ExpectedConditions.elementToBeClickable(CasinoGames.debugComponent.get(0)));
                CasinoGames.debugComponent.get(0).click();
            }

            //WebElement for play button and will wait for 20 secs
            WebElement playButton = CasinoGames.playButton;
            wait.until(ExpectedConditions.elementToBeClickable(playButton));

            //Check if play button is present, if yes then click
            boolean playButton_isPresent = playButton.isDisplayed();
            if(playButton_isPresent){
                wait.until(ExpectedConditions.elementToBeClickable(playButton));
                playButton.click();

            } else {
                WebElement gameScreen = CasinoGames.canvasPlayButton;
                wait.until(ExpectedConditions.visibilityOf(gameScreen));

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

            //Forgot what is this
            int overlay_message = driver.findElements(By.xpath(".//div[@class='v-dialog__content v-dialog__content--active']//div[@class='v-card__text dark-overlay message-contents']")).size();
            if(overlay_message == 1){
                WebElement overlay_exitButton = driver.findElement(By.xpath(".//button[@class='v-btn v-btn--icon theme--dark']"));
                wait.until(ExpectedConditions.elementToBeClickable(overlay_exitButton));
                overlay_exitButton.click();
            }

            //WebElements for inner play button, balance display and win indicator
            WebElement innerPlayButton = CasinoGames.innerPlayButton;
            WebElement balanceDisplay = CasinoGames.balanceValue;

            //Wait for balance to display first before getting the value of the balance
            longwait.until(ExpectedConditions.visibilityOf(balanceDisplay));

            BigDecimal bd_winnings;
            BigDecimal bd_stake;
            String result = "Lose";
            String settlementStatus = "Not Tally";

            do {
                //Wait for autoplay button to be enabled
                int autoPlay_isDisabled = CasinoGames.autoPlayButton.size();
                if(autoPlay_isDisabled == 0) {

                    balanceBeforeBetNoFormat = balanceDisplay.getText(); //Get balance before betting
                    actualBalanceBeforeBet = balanceBeforeBetNoFormat
                            .replace("PHP", "")
                            .replace(",", "")
                            .replace(".00", "");

                    BigDecimal bd_actualBalanceBeforeBet = new BigDecimal(actualBalanceBeforeBet);

                    //Get text and remove the strings inside the stake
                    stakeValue = CasinoGames.stakeValue.getText()
                            .replace("PHP", "")
                            .replace(",", "")
                            .replace(".00", ""); //Get the stake amount

                    bd_stake = new BigDecimal(stakeValue);

                    //Wait for play button to be clickable.
                    wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
                    innerPlayButton.click();

                    //System.out.println("----------------- Start betting: " + gameName);
                    System.out.println("\nBalance before bet: " + balanceBeforeBetNoFormat);

                    //Check if autoplay button is disabled
                    int autoPlay_isDisabled2;
                    do{
                        autoPlay_isDisabled2 = CasinoGames.autoPlayButton.size();

                    } while (autoPlay_isDisabled2 != 0);

                    Thread.sleep(500);
                    //Get the balance after betting and remove the word "PHP"
                    actualBalanceAfterBet = balanceDisplay.getText()
                            .replace("PHP", "")
                            .replace(",", "")
                            .replace(".00","");

                    //Start of computation
                    BigDecimal bd_actualBalanceAfterBet = new BigDecimal(actualBalanceAfterBet);
                    System.out.println("actualBalanceAfterBet: " + actualBalanceAfterBet);

                    BigDecimal bd_expectedBalanceAfterBet = bd_actualBalanceBeforeBet.subtract(bd_stake);
                    BigDecimal bd_expectedBalanceAfterWin = null;
                    System.out.println("bd_expectedBalanceAfterBet: " + bd_expectedBalanceAfterBet);

                    //To check if win or lose; if difference is equal to the (stake * -1) or if the difference is different from the (stake * -1)
                    bd_winnings = (bd_actualBalanceAfterBet.subtract(bd_actualBalanceBeforeBet));
                    System.out.println("bd_winnings: " + bd_winnings);

                    //Start of conditions
                    //LOSE
                    if (bd_winnings.equals(bd_stake.negate())) {
                        System.out.println("result: " + result);
                        System.out.println("Stake: " + bd_stake + " | Winnings: " + bd_winnings);

                        if (bd_expectedBalanceAfterBet.equals(bd_actualBalanceAfterBet)) {
                            betStatus = "Tally";
                        } else {
                            betStatus = "Not Tally";
                        }
                        System.out.println("Balance after bet: " + bd_actualBalanceAfterBet +
                                "\nStatus: " + betStatus);

                    //WINNING
                    } else {
                        result = "Win";
                        System.out.println("result: " + result);
                        System.out.println("Stake: " + bd_stake + " | Winnings: " + bd_winnings);

                        bd_expectedBalanceAfterWin = (bd_actualBalanceBeforeBet.add(bd_winnings));

                        if (bd_expectedBalanceAfterWin.equals(bd_actualBalanceAfterBet)) {
                            settlementStatus = "Tally";
                            System.out.println("\nbd_expectedBalanceAfterWin " + bd_expectedBalanceAfterWin);
                            System.out.println("bd_actualBalanceAfterBet " + bd_actualBalanceAfterBet + "\n");

                        } else {
                            settlementStatus = "Not Tally";
                            System.out.println("\n bd_expectedBalanceAfterWin " + bd_expectedBalanceAfterWin);
                            System.out.println("bd_actualBalanceAfterBet " + bd_actualBalanceAfterBet + "\n");
                        }
                        System.out.println("Balance after winning: " + balanceDisplay.getText() +
                                "\nStatus: " + settlementStatus);
                    }

                    //Assigning value for the variable "report"
                    report = ("Balance before betting: " + balanceBeforeBetNoFormat + "%0A" +
                            "Stake: " + bd_stake + "  |  Winnings: " + bd_winnings + "%0A" +
                            "Balance after winning: " + balanceDisplay.getText() + "%0A" +
                            "Status: " + settlementStatus + "%0A");

                    //Check the result if won; if yes append else do not append
                    if (result.equals("Win")) {
                        resultContent.append(report);
                    }

                }

            } while (!result.equals("Win"));

        } else {
            System.out.println("The game is under maintenance.");
        }

    }

    String provider = GamesCasino.provider;
    StringBuffer resultContent = new StringBuffer();
    @Then("I send RT betting result in telegram")
    public void sendRTBettingResult(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        String resultContentString = resultContent.toString();
        String username = Login.user;

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+
                    "&text=Provider: "+provider+"%0AUsername: "+username+"%0A"+resultContentString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status + ": " + url);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
