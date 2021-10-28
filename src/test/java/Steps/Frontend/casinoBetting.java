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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
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
    public void selectProvider(DataTable providerDetails) throws InterruptedException {
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

        CSVReader reader = new CSVReader(new FileReader("D:\\ids.csv"));
        int rows = 0;
        String csvCell[];

        while ((csvCell = reader.readNext()) != null) {
            String game_id = csvCell[0];
            rows++;

            //Delay
            Thread.sleep(1500);

            //Creating object of an Actions class
            WebElement gameCard = base.Driver.findElement(By.xpath("//div[@class='game-content']/div/div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3' and position()="+rows+"]"));

            //Performing the mouse hover action on the target element.
            boolean gameCard_isPresent = gameCard.isDisplayed();
            if (gameCard_isPresent) {
                wait.until(ExpectedConditions.visibilityOf(gameCard));
                action.moveToElement(gameCard).perform();
            }

            WebElement extracted_gameId = base.Driver.findElement(By.xpath("//a[@href='/launch-game/"+game_id+"/play?scc=1']"));
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

            //WebElement for iframe and switch focus to iframe
            WebElement iframe = page.gameIframe;

            longwait.until(ExpectedConditions.visibilityOf(iframe));
            boolean iframe1_isPresent = iframe.isDisplayed();
            if (iframe1_isPresent) {
                System.out.println("frame 1 displayed");
                longwait.until(ExpectedConditions.visibilityOf(iframe));
                base.Driver.switchTo().frame(iframe);

                Thread.sleep(1500);
                WebElement loadingScreen = base.Driver.findElement(By.xpath("//div[@class='loading-screen']"));
                //longwait.until(ExpectedConditions.invisibilityOf(loading));
                boolean loading_isPresent = loadingScreen.isDisplayed();
                    while(loading_isPresent){
                        Thread.sleep(5000);
                        System.out.println("Loading is still displayed.");
                        boolean loading_isStillPresent = loadingScreen.isDisplayed();

                        if(!loading_isStillPresent){
                            System.out.println("Loading is not displayed.");
                            break;
                        }
                    }

                }

            //WebElement for iframe and switch focus to iframe
            WebElement iframe2 = page.gameIframe2;
            longwait.until(ExpectedConditions.visibilityOf(iframe2));
            base.Driver.switchTo().frame(iframe2);

            System.out.println("Done switching to iframe2");

            //WebElement for play button and will wait for 20 secs
            WebElement playButton = page.playButton;
            longwait.until(ExpectedConditions.visibilityOf(playButton));
            playButton.click();

            //WebElement for inner play button and will wait for 20 secs
            WebElement innerPlayButton = page.innerPlayButton;
            WebElement balanceValue = page.balanceValue;

            longwait.until(ExpectedConditions.visibilityOf(balanceValue));
            boolean balanceValue_isPresent = balanceValue.isDisplayed();
            if(balanceValue_isPresent){
                wait.until(ExpectedConditions.visibilityOf(balanceValue));
                wait.until(ExpectedConditions.elementToBeClickable(innerPlayButton));
                innerPlayButton.click();
                System.out.println("Done clicking the inner play button.");
            }

           //base.Driver.switchTo().defaultContent();

        }
    }
}
