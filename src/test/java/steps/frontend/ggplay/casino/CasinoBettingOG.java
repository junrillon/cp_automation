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
import steps.frontend.GamesCasino;
import steps.frontend.Login;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CasinoBettingOG {
    private final WebDriver driver;

    public CasinoBettingOG(Driver driver) {
        this.driver = driver.get();
    }

    @When("I play game")
    public void iPlayGame() throws InterruptedException {

        CasinoGames CasinoGames = new CasinoGames(driver);
        Actions action = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, 40);
        WebDriverWait longwait = new WebDriverWait(driver, 60);

        String report;
        String result;

        String settlementStatus = null;
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
            WebElement playButton = CasinoGames.ogPlayButton;
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

            int int_winnings;
            int int_stake;
            do {

                boolean balanceValue_isPresent = balanceDisplay.isDisplayed();
                actualBalanceBeforeBet = balanceDisplay.getText(); //Get balance before betting
                balanceBeforeBetNoFormat = actualBalanceBeforeBet;
                stakeValue = CasinoGames.stakeValue.getText(); //Get the stake amount

                if (balanceValue_isPresent) {
                    wait.until(ExpectedConditions.visibilityOf(balanceDisplay));
                    wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
                    innerPlayButton.click();
                }

                //System.out.println("----------------- Start betting: " + gameName);
                System.out.println("\nBalance before bet: " + actualBalanceBeforeBet);

                //Remove the strings inside the balance
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace("PHP", "");
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace(",", "");
                actualBalanceBeforeBet = actualBalanceBeforeBet.replace(".00", "");

                //Remove the strings inside the stake
                stakeValue = stakeValue.replace("PHP", "");
                stakeValue = stakeValue.replace(",", "");
                stakeValue = stakeValue.replace(".00", "");


                //Wait until the current balance is not the same with the balance before bet.
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(balanceDisplay, balanceBeforeBetNoFormat)));
//                try {
//                    boolean checkBalance = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(balanceDisplay, balanceBeforeBetNoFormat)));
//                    System.out.println("The balance is still the same.");
//
//                } catch (TimeoutException e) {
//                    //boolean checkBalance = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(balanceDisplay, balanceBeforeBetNoFormat)));
//                    wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
//                    innerPlayButton.click();
//                    System.out.println("Click play button 2");
//
//                }

                //Get the balance after betting and remove the word "PHP"
                actualBalanceAfterBet = CasinoGames.balanceValue.getText();
                actualBalanceAfterBet = actualBalanceAfterBet.replace("PHP", "");
                actualBalanceAfterBet = actualBalanceAfterBet.replace(",", "");
                actualBalanceAfterBet = actualBalanceAfterBet.replace(".00", "");

                //Start of computation
                int_stake = Integer.parseInt(stakeValue);
                int int_actualBalanceBeforeBet = Integer.parseInt(actualBalanceBeforeBet);
                int int_actualBalanceAfterBet = Integer.parseInt(actualBalanceAfterBet);
                int int_expectedBalanceAfterBet = int_actualBalanceBeforeBet - int_stake;
                int int_expectedBalanceAfterWin;

                //To check if win or lose; if difference is equal to the (stake * -1) or if the difference is different from the (stake * -1)
                int_winnings = (int_actualBalanceAfterBet - int_actualBalanceBeforeBet);

                //Start of conditions
                //LOSE
                if (int_winnings == (int_stake * -1)) {
                    result = "Lose";
                    System.out.println("Stake: " + int_stake + " | Winnings: " + int_winnings);

                    if (int_expectedBalanceAfterBet == int_actualBalanceAfterBet) {
                        betStatus = "Tally";
                    } else {
                        betStatus = "Not Tally";
                    }
                    System.out.println("Balance after bet: " + int_actualBalanceAfterBet +
                            "\nStatus: " + betStatus);

                    //WINNING
                } else {
                    result = "Win";
                    System.out.println("Stake: " + int_stake + " | Winnings: " + int_winnings);
                    int_expectedBalanceAfterWin = int_actualBalanceBeforeBet + int_winnings;

                    if (int_expectedBalanceAfterWin == int_actualBalanceAfterBet) {
                        settlementStatus = "Tally";
                    } else {
                        settlementStatus = "Not Tally";
                    }
                    System.out.println("Balance after winning: " + balanceDisplay.getText() +
                            "\nStatus: " + settlementStatus);
                }

                //Assigning value for the variable "report"
                report = ("Balance before betting: " + balanceBeforeBetNoFormat + "%0A" +
                        "Stake: " + int_stake + "  |  Winnings: " + int_winnings + "%0A" +
                        "Balance after winning: " + balanceDisplay.getText() + "%0A" +
                        "Status: " + settlementStatus + "%0A");

                //Check the result if won; if yes append else do not append
                if(result.equals("Win")){
                    resultContent.append(report);
                }

                //delay 2s
                Thread.sleep(2000);
            } while (int_winnings == (int_stake * -1));

        } else {
            System.out.println("The game is under maintenance.");
        }

    }

    String provider = GamesCasino.provider;
    StringBuffer resultContent = new StringBuffer();
    @Then("I send OG betting result in telegram")
    public void sendOGBettingResult(DataTable telegramCreds){
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
