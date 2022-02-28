package Steps.Backoffice;

import Base.BaseUtil;
import Pages.Backoffice.Backoffice;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Login {
    private BaseUtil base;

    public Login(BaseUtil base) {
        this.base = base;
    }

    @Given("^I access b2c backoffice ([^\"]*)$")
    public void IAccessB2cBackoffice(String BOUrl) throws CsvValidationException, IOException, InterruptedException {
        //navigate to backoffice
        base.Driver.navigate().to(BOUrl);
    }

    @When("^input the BO Username ([^\"]*) and Password ([^\"]*)$")
    public void inputTheUsernameAndPassword(String usernameAdmin, String passwordAdmin) throws InterruptedException{
        //Input username and password
        Backoffice page = new Backoffice(base.Driver);
        WebElement login = page.loginButton;
        //Wait
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

        //Get current website title
        String currentPage = base.Driver.getTitle();
        System.out.println(currentPage);
        if(currentPage.contains("Dashboard")){
            System.out.println("You're already logged in!");
        } else {
            page.inputCredentials(usernameAdmin, passwordAdmin);
            wait.until(ExpectedConditions.elementToBeClickable(login));
            page.clickLoginButton();
            page.adminUsernameDisplay();
        }
    }

    @And("go to gamelist")
    public void goToGamelist() {
        Backoffice page = new Backoffice(base.Driver);
        WebElement nav_casino = page.navCasino;
        //Wait
        WebDriverWait wait = new WebDriverWait(base.Driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(nav_casino));
        //Go to game list page
        page.navigateToGamelist();

    }

    @And("select provider ([^\"]*)$")
    public void selectProvider(String provider) {
        Backoffice page = new Backoffice(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
        String pageTitle = page.pageTitle.getText();

        //Check if you're on game list page
        if (!pageTitle.equalsIgnoreCase("Game List Management")) {
            Assert.fail("You're in the wrong page");
        }

        //wait for provider selection
        wait.until(ExpectedConditions.visibilityOf(page.providerSelection));

        //Locate click the provider
        WebElement selectedProvider1 = base.Driver.findElement(By.xpath("//li/a[@class='toggle']/span[contains(text(),'" + provider + "')]"));
        String actualProvider = selectedProvider1.getText();
        if (!actualProvider.equalsIgnoreCase(provider)) {
            Assert.fail("Expected: " + provider + " " + "Actual Provider: " + actualProvider);
        }
        System.out.println("Expected: " + provider + " " + " | Actual Provider: " + actualProvider);
        selectedProvider1.click();

        //2nd click to the provider
        WebElement selectedProvider2 = base.Driver.findElement(By.xpath("//ul[@class='inner show']/li/a/span[contains(text(),'" + provider + "')]"));
        selectedProvider2.click();

        wait.until(ExpectedConditions.visibilityOf(page.modalCloseButton));
        page.modalCloseButton.click();
    }

//
//    @And("check all the games without image under provider ([^\"]*)$")
//    public void checkAllTheGamesWithoutImageUnderProvider(String provider) {
//        Backoffice page = new Backoffice(base.Driver);
//        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
//
//        WebElement gNameFilter = page.gameNameFilter;
//        WebElement applyFilter = page.applyFilterButton;
//        WebElement gameImage = page.gameImage;
//        WebElement uploadImageModal = page.uploadImageForm;
//        WebElement chooseFileButton = page.chooseFileButton;
//        WebElement saveButton = page.saveButton;
//        WebElement closeButton = page.uploadImageFormCloseButton;
//        WebElement successNote = page.noteSuccess;
//
//        WebElement casinoRoomFilter = page.casinoRoomFilter;
//        Select casinoRoomDrpDown = new Select(casinoRoomFilter);
//        casinoRoomDrpDown.selectByVisibleText(provider);
//
//        try {
//            List<String> games = new ArrayList<>();
//            //get match id
//            String sql = "SELECT id, game_name  FROM casino_games WHERE category_name = '"+provider+"'" +
//                    "AND is_active = 1";
//            ResultSet casinoGames = DataBaseConnection.execDBQuery(sql);
//            casinoGames.beforeFirst();
//
//            List<String> gs = new ArrayList<String>();
//            String x = null;
//            while (casinoGames.next()){
//                String game_name = casinoGames.getString("game_name");
//                gs.add(game_name);
//                int size = gs.size();
//                for(int i = 0; i < size; i++) {
//                    x = gs.get(i);
//
//                    gNameFilter.sendKeys(x);
//                    casinoRoomDrpDown.selectByVisibleText(provider);
//                    applyFilter.click();
//
//                    wait.until(ExpectedConditions.visibilityOf(page.modalCloseButton));
//                    wait.until(ExpectedConditions.elementToBeClickable(page.modalCloseButton));
//                    page.modalCloseButton.click();
//
//                    wait.until(ExpectedConditions.visibilityOf(gameImage));
//                    wait.until(ExpectedConditions.elementToBeClickable(gameImage));
//                    gameImage.click();
//
//                    wait.until(ExpectedConditions.visibilityOf(uploadImageModal));
//
//                    boolean present = chooseFileButton.isDisplayed();
//                    if(present){
//                        chooseFileButton.sendKeys("D:\\CP Files\\Casino Provider\\Evolution\\4012_baccarat.png");
//                        String expectedFile = chooseFileButton.getAttribute("value");
//                        System.out.println(expectedFile);
//
//                        if(expectedFile.equalsIgnoreCase("C:\\fakepath\\4012_baccarat.png")){
//                            System.out.println("Uploading Success!");
////                            wait.until(ExpectedConditions.elementToBeClickable(saveButton));
////                            saveButton.click();
//
//                            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
//                            closeButton.click();
//
//                        } else {
//                            System.out.println("Uploading failed!");
//                        }
//
//                    } else {
//                        Assert.fail(chooseFileButton + " is not present!");
//                    }
//
////                    boolean note_success = successNote.isDisplayed();
////                    if(note_success){
////                        System.out.println("Success note is  displayed");
////                    } else {
////                        Assert.fail("Success note is not displayed.");
////                    }
//                    gNameFilter.sendKeys(Keys.CONTROL + "a");
//                    gNameFilter.sendKeys(Keys.DELETE);
//
//                    gs.remove(i);
//                }
//                Thread.sleep(1000);
//                System.out.println("Casino Game: " + x);
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @And("check all the games without image under provider using excel")
    public void checkAllTheGamesWithoutImageUnderProviderUsingExcel(DataTable data) throws IOException, InterruptedException, CsvValidationException {
        Backoffice page = new Backoffice(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

        List<List<String>> source = data.asLists(String.class);
        String provider = source.get(1).get(0);
        String fileType = source.get(1).get(1);
        String location = source.get(1).get(2);

        WebElement gDetailsCloseButton = page.modalCloseButton;
        WebElement gNameFilter = page.gameNameFilter;
        WebElement applyFilter = page.applyFilterButton;
        WebElement gameImage = page.gameImage;
        WebElement uploadImageModal = page.uploadImageForm;
        WebElement chooseFileButton = page.chooseFileButton;
        WebElement saveButton = page.saveButton;
        WebElement closeButton = page.uploadImageFormCloseButton;
        WebElement successNote = page.noteSuccess;

        WebElement casinoRoomFilter = page.casinoRoomFilter;
        Select casinoRoomDrpDown = new Select(casinoRoomFilter);
        casinoRoomDrpDown.selectByVisibleText(provider);

        CSVReader reader = new CSVReader(new FileReader("D:\\Book.csv"));
        String csvCell[];
        String fakePath = "C:\\fakepath\\";

        while ((csvCell = reader.readNext()) != null) {
            String game_name = csvCell[0];
            String fileName = csvCell[1];

            //Clear Game Name filter before send keys
            gNameFilter.sendKeys(Keys.CONTROL + "a");
            gNameFilter.sendKeys(Keys.DELETE);

            //Input game name from CSV File to game name filter
            gNameFilter.sendKeys(game_name);
            casinoRoomDrpDown.selectByVisibleText(provider); //<-- Select provider in casino room dropdown
            applyFilter.click(); //<-- Click apply filter button

            Thread.sleep(1000);

            //Check if modalCloseButton isPresent
            int gameDetails = base.Driver.findElements(By.xpath("//div[@class='modal-dialog modal-sm']//h5[contains(text(),\"Game Details\")]")).size();
            if(gameDetails > 0){
                wait.until(ExpectedConditions.elementToBeClickable(gDetailsCloseButton));
                page.modalCloseButton.click();
            }

            Thread.sleep(1000);

            //Check if gameImage isPresent
            wait.until(ExpectedConditions.visibilityOf(gameImage));
            boolean gameImage_isPresent = gameImage.isDisplayed();
            if (gameImage_isPresent) {
                wait.until(ExpectedConditions.elementToBeClickable(gameImage));
                gameImage.click();
            } else {
                Assert.fail("Game image object is not present.");
            }

            //Wait for Upload Image Modal
            wait.until(ExpectedConditions.visibilityOf(uploadImageModal));

            //Check if gameImage isPresent
            wait.until(ExpectedConditions.visibilityOf(chooseFileButton));
            boolean chooseFileButton_isPresent = chooseFileButton.isDisplayed();
            if (chooseFileButton_isPresent) {
                chooseFileButton.sendKeys(location + fileName + fileType);
                String actualFile = chooseFileButton.getAttribute("value");

                if (actualFile.equalsIgnoreCase(fakePath + fileName + fileType)) {
                    System.out.println(actualFile);
                    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
                    saveButton.click();
//                    wait.until(ExpectedConditions.elementToBeClickable(closeButton));
//                    closeButton.click();

                } else {
                    Assert.fail("Check the file. " +
                            "\n Expected File: " + fileName + fileType + ", Actual File: " + actualFile);
                }

            } else {
                Assert.fail("Choose File button is not present!");
            }

            Thread.sleep(1000);

            //Check if note_success isPresent
            wait.until(ExpectedConditions.visibilityOf(successNote));
            boolean noteSuccess_isPresent = successNote.isDisplayed();
            if (noteSuccess_isPresent) {
                System.out.println("Image Successfully uploaded. \n---------------");
            } else {
                Assert.fail("Success note is not displayed.");
            }
//            gNameFilter.sendKeys(Keys.CONTROL + "a");
//            gNameFilter.sendKeys(Keys.DELETE);
        }
    }
}
