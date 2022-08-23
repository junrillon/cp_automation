package steps.frontend.ggplay.casino;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.Dashboard;
import pages.frontend.ggplay.casino.LiveGames;
import steps.frontend.GamesCasino;
import steps.frontend.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class EvolutionBetting {
    private final WebDriver driver;

    StringBuffer resultContent = new StringBuffer();
    public EvolutionBetting(Driver driver) {
        this.driver = driver.get();
    }

    String inGame_BalBeforeBet;
    String inGame_BalBeforeBet_formatted;

    String inGame_BalAfterBet;
    String inGame_BalAfterBet_formatted;

    String winnings;
    String winnings_formatted;

    String betValue;
    String betLabel;
    String totalBet_formatted;

    BigDecimal expectedBalanceAfterBet;
    BigDecimal actualBalanceAfterWin;

    String result;

    @When("I select live game")
    public void iSelectLiveGame() throws InterruptedException {
        LiveGames liveGames = new LiveGames(driver);
        Actions action = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Performing the mouse hover action on the target element.
        wait.until(ExpectedConditions.visibilityOf(liveGames.gameCard));
        action.moveToElement(liveGames.gameCard).perform();
        System.out.println("Hover at game card");

        //Wait for play button to be visible and clickable
        wait.until(ExpectedConditions.visibilityOf(liveGames.gameCardPlayButton));
        wait.until(ExpectedConditions.elementToBeClickable(liveGames.gameCardPlayButton));
        liveGames.gameCardPlayButton.click();
        System.out.println("Click play button");


    }

    public void clickRecentGame(){
        LiveGames liveGames = new LiveGames(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOf(liveGames.recentlyPlayed));
        wait.until(ExpectedConditions.visibilityOf(liveGames.firstRecentlyPlayedGame));
        wait.until(ExpectedConditions.elementToBeClickable(liveGames.firstRecentlyPlayedGame));
        liveGames.firstRecentlyPlayedGame.click();

    }

    @When("I play live casino")
    public void iPlayLiveCasino() throws InterruptedException {
        Dashboard dashboard = new Dashboard(driver);
        LiveGames liveGames = new LiveGames(driver);

        String oldTab = driver.getWindowHandle();

        String inGame_BalAfterWin;
        String inGame_BalAfterWin_formatted;


        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebDriverWait longwait = new WebDriverWait(driver, 60);

        String checkIfMaintenance = liveGames.maintenanceNotif.getText();
        if(!checkIfMaintenance.contains("Game under maintenance")){
            //WebElement for iframe and switch focus to iframe
            WebElement iframe = liveGames.evoLobbyIframe;

            longwait.until(ExpectedConditions.visibilityOf(iframe));
            boolean iframe1_isPresent = iframe.isDisplayed();
            if (iframe1_isPresent) {
                //Wait and switch focus to iframe1
                longwait.until(ExpectedConditions.visibilityOf(iframe));
                driver.switchTo().frame(iframe);
                System.out.println("Done switching to iframe");

                //Check and wait for loading screen
                int loading_isPresent;
                do {
                    loading_isPresent = liveGames.casinoLoadingScreen.size();

                } while (loading_isPresent != 0);
            }

            //Call clickRecentGame
            clickRecentGame();

            //Check and wait for loading screen to finish
            int finishLoading_isPresent;
            do {
                finishLoading_isPresent = liveGames.casinoFinishProgress.size();

            } while (finishLoading_isPresent <= 0);

            //Will bet until it wins
            boolean notWinning = true;
            String isCollapsed;
            while(notWinning) {
                isCollapsed = liveGames.betAmountContainer.getAttribute("data-is-collapsed");

                //element for minBetAmount, and then get the attribute data-value
                WebElement minBetAmount = liveGames.minBetAmount;
                betValue = minBetAmount.getAttribute("data-value");

                //element for to
                // tal bet display, wait for element to visible
                WebElement totalBetDisplay = liveGames.totalBet;
                wait.until(ExpectedConditions.visibilityOf(totalBetDisplay));

                //get the text of the totalBetDisplay, then remove the comma
                String totalBet = totalBetDisplay.getText();
                totalBet_formatted = totalBet.replace(",", "");

                //Check if betting is open
                if (isCollapsed.equalsIgnoreCase("false")) {
                    if (totalBet_formatted.equals("0")) {

                        //element for balance display, wait for element to visible
                        wait.until(ExpectedConditions.visibilityOf(liveGames.inGameBalance));
                        WebElement inGameBalance = liveGames.inGameBalance;

                        //get the text of the inGameBalance, then remove the comma and PHP
                        inGame_BalBeforeBet = inGameBalance.getText();
                        inGame_BalBeforeBet_formatted = inGame_BalBeforeBet.replace(",","");

                        System.out.println("\nBalance before bet: " + inGame_BalBeforeBet);
                        //resultContent.append("Balance before bet: ").append(inGame_BalBeforeBet).append("%0A");

                        if (isCollapsed.equalsIgnoreCase("false")) {

                            //wait for betAmountSelection to be visible and minBetAmount to be clickable
                            wait.until(ExpectedConditions.visibilityOf(liveGames.betAmountSelection));
                            wait.until(ExpectedConditions.elementToBeClickable(minBetAmount));
                            minBetAmount.click();


                            int playerSelection_isDisplayed = liveGames.playerSelection.size();
                            int dragonSelection_isDisplayed  = liveGames.dragonSelection.size();

                            if(playerSelection_isDisplayed > 0){
                                //element for playerSelection, wait for element to be clickable
                                WebElement playerSelection = liveGames.playerSelection.get(0);
                                wait.until(ExpectedConditions.elementToBeClickable(playerSelection));
                                playerSelection.click();

                                System.out.println("Player Selection: " + true);


                                //element for playerSelectionLabel, and then get the text
                                WebElement playerSelectionLabel = liveGames.playerSelectionLabel;
                                betLabel = playerSelectionLabel.getText();

                            } else if(dragonSelection_isDisplayed > 0){
                                //element for dragonSelection, wait for element to be clickable
                                WebElement dragonSelection = liveGames.dragonSelection.get(0);
                                wait.until(ExpectedConditions.elementToBeClickable(dragonSelection));
                                dragonSelection.click();

                                System.out.println("Dragon Selection: " + true);

                                //element for dragonSelectionLabel, and then get the text
                                WebElement dragonSelectionLabel = liveGames.dragonSelectionLabel;
                                betLabel = dragonSelectionLabel.getText();

                            } else {

                                System.out.println("Player/Dragon Selection: " + false);
                            }

                        }

                        System.out.println("Bet Amount: "+ betValue +
                                "\nBet Selection: " + betLabel);

                        inGame_BalAfterBet = liveGames.inGameBalance.getText();

                    }

                    Thread.sleep(1000);
                } else {

                   if(!totalBet_formatted.equals("0")) {
                        int size = liveGames.gameResultElements.size();
                        if (size > 0) {
                            int youWinMessage = liveGames.gameResultMessageContainer.size();
                            if (youWinMessage > 0) {
                                System.out.println("You Win");
                                winnings = liveGames.winnings.getText();
                                winnings_formatted = winnings.replace("PHP","")
                                        .replace("R$", "")
                                        .replace(",","")
                                        .replace(" ","");


                                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(liveGames.inGameBalance, inGame_BalAfterBet)));
                                Thread.sleep(1000);

                                inGame_BalAfterBet_formatted = inGame_BalAfterBet.replace(",","");

                                BigDecimal BB = new BigDecimal(inGame_BalAfterBet_formatted);
                                BigDecimal BA = new BigDecimal(winnings_formatted);
                                BigDecimal expectedBalanceAfterWin = BB.add(BA);

                                inGame_BalAfterWin = liveGames.inGameBalance.getText();
                                inGame_BalAfterWin_formatted = inGame_BalAfterWin.replace(",","")
                                        .replace("PHP","")
                                        .replace("R$", "");

                                actualBalanceAfterWin = new BigDecimal(inGame_BalAfterWin_formatted);

                                if(expectedBalanceAfterWin.equals(actualBalanceAfterWin)){
                                    Assert.assertEquals(expectedBalanceAfterWin,actualBalanceAfterWin);
                                    System.out.println("Winnings: " + winnings +
                                            "\nBalance after win: " + inGame_BalAfterWin);

                                    //Switch back to old window handler
                                    driver.switchTo().window(oldTab);

                                    wait.until(ExpectedConditions.visibilityOf(dashboard.walletBalance));
                                    String navBalance = dashboard.walletBalance.getText();
                                    String navBalance_formatted = navBalance
                                            .replace(",", "")
                                            .replace(".00", "")
                                            .replace("R$ ", "");

                                    System.out.println("Nav balance: "+navBalance);

                                    if(expectedBalanceAfterWin.toString().equals(navBalance_formatted)){
                                        result = ("Balance before bet: " + inGame_BalBeforeBet + "%0A" +
                                                    "Bet Amount: "+ betValue + "%0A" +
                                                    "Bet Selection: " + betLabel + "%0A" +
                                                    "Winnings: " + winnings + "%0A" +
                                                    "Balance after win: " + inGame_BalAfterWin + "%0A");

                                        resultContent.append(result);
                                        notWinning = false;

                                    } else {
                                        System.out.println("expectedBalanceAfterWin not equal to navBalance_formatted");
                                    }

                                } else {
                                    System.out.println("Balance not tally after win");
                                }
                            }

                            Thread.sleep(1000);
                        } else {
                            BigDecimal BB = new BigDecimal(inGame_BalBeforeBet_formatted);
                            BigDecimal BA = new BigDecimal(betValue);
                            expectedBalanceAfterBet = BB.subtract(BA);
                            BigDecimal formatted_expectedBalanceAfterBet = new BigDecimal(String.valueOf(expectedBalanceAfterBet))
                                                                            .setScale(2, RoundingMode.HALF_UP);

                            inGame_BalAfterBet = liveGames.inGameBalance.getText();
                            inGame_BalAfterBet_formatted = inGame_BalAfterBet.replace(",", "");
                            BigDecimal AB = new BigDecimal(inGame_BalAfterBet_formatted)
                                            .setScale(2, RoundingMode.HALF_UP);

                            //Assert if expected balance is equals to actual balance after bet
                            Assert.assertEquals(formatted_expectedBalanceAfterBet, AB);
                        }
                    }
                }
            }

            Thread.sleep(1500);
        } else {
            System.out.println("The game is under maintenance.");
        }

    }

    String provider = GamesCasino.provider;
    @Then("I send EVO betting result in telegram")
    public void sendEVOBettingResult(DataTable telegramCreds){
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
