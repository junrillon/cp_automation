package steps.backoffice;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.backoffice.GameList;
import pages.backoffice.Dashboard;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class UploadGameImage {
    private final WebDriver driver;
    public PageModelBase page;

    public UploadGameImage (PageModelBase page, Driver driver){
        this.driver = driver.get();
        this.page = page;
    }

    @Given("I go to gamelist page")
    public void IGoToGamelistPage() {
        Dashboard page = new Dashboard(driver);
        WebElement nav_casino = page.navCasino;

        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(nav_casino));

        //wait for Casino button to be clickable and then click
        wait.until(ExpectedConditions.elementToBeClickable(page.navCasino));
        page.navCasino.click();

        //wait for Game List Management button to be clickable and then click
        wait.until(ExpectedConditions.elementToBeClickable(page.navCasino_GameListManagement));
        page.navCasino_GameListManagement.click();

        }

    @When("I upload the image per game")
    public void iUploadImagePerGame(DataTable FileDetails) throws IOException, CsvValidationException, InterruptedException {
        GameList GameList = new GameList(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //File Details
        List<List<String>> source = FileDetails.asLists(String.class);
        String provider = source.get(1).get(0);
        String fileType = source.get(1).get(1);
        String fileLocation = source.get(1).get(2);
        String csvLocation = source.get(1).get(3);
        Select casinoRoomDrpDown = new Select(GameList.casinoRoomFilter);
        casinoRoomDrpDown.selectByVisibleText(provider);

        CSVReader reader = new CSVReader(new FileReader(csvLocation));
        String[] csvCell;
        String fakePath = "C:\\fakepath\\";

        while ((csvCell = reader.readNext()) != null) {
            String game_name = csvCell[0];
            String fileName = csvCell[1];

            //Wait for data table to load before game name search
            wait.until(ExpectedConditions.visibilityOf(GameList.dataTableEmpty));

            //Clear Game Name filter before send keys
            wait.until(ExpectedConditions.visibilityOf(GameList.gameNameFilter));
            GameList.gameNameFilter.sendKeys(Keys.CONTROL + "a");
            GameList.gameNameFilter.sendKeys(Keys.DELETE);

            //Input game name from CSV File to game name filter
            GameList.gameNameFilter.sendKeys(game_name);
            wait.until(ExpectedConditions.visibilityOf(GameList.casinoRoomFilter));
            casinoRoomDrpDown.selectByVisibleText(provider); //<-- Select provider in casino room dropdown

            //wait for apply button to be clickable
            wait.until(ExpectedConditions.visibilityOf(GameList.applyFilterButton));
            wait.until(ExpectedConditions.elementToBeClickable(GameList.applyFilterButton))
                    .click(); //<-- Click apply filter button
            System.out.println("Clicked apply button");

            Thread.sleep(1000);
            //Check if modalCloseButton isPresents
            int gameDetails = GameList.gDetailsModal.size();
            if(gameDetails > 0){
                page.waitForVisibility(GameList.WE_gDetailsModal,20);
                page.waitForVisibility(GameList.modalCloseButton,20);
                wait.until(ExpectedConditions.elementToBeClickable(GameList.modalCloseButton));
                GameList.modalCloseButton.click();

            } else {
                System.out.println("Game Details Modal Closed.");
            }

            //Check if gameImage isPresent
            wait.until(ExpectedConditions.visibilityOf(GameList.gameImage));
            boolean gameImage_isPresent = GameList.gameImage.isDisplayed();
            if (gameImage_isPresent) {
                page.waitForVisibility(GameList.gameImage,20);
                wait.until(ExpectedConditions.elementToBeClickable(GameList.gameImage));
                GameList.gameImage.click();
            } else {
                Assert.fail("Game image object is not present.");
            }

            //
            wait.until(ExpectedConditions.visibilityOf(GameList.uploadImageForm));

            //Check if gameImage isPresent
            wait.until(ExpectedConditions.visibilityOf(GameList.chooseFileButton));
            boolean chooseFileButton_isPresent = GameList.chooseFileButton.isDisplayed();
            if (chooseFileButton_isPresent) {
                GameList.chooseFileButton.sendKeys(fileLocation + fileName + fileType);
                String actualFile = GameList.chooseFileButton.getAttribute("value");

                if (actualFile.equalsIgnoreCase(fakePath + fileName + fileType)) {
                    System.out.println(actualFile);
                    wait.until(ExpectedConditions.elementToBeClickable(GameList.saveButton));
                    GameList.saveButton.click();

                } else {
                    Assert.fail("Check the file. " +
                            "\n Expected File: " + fileName + fileType + ", Actual File: " + actualFile);
                }

            } else {
                Assert.fail("Choose File button is not present!");
            }

            //Check if note_success isPresent
            wait.until(ExpectedConditions.visibilityOf(GameList.noteSuccess));
            boolean noteSuccess_isPresent = GameList.noteSuccess.isDisplayed();
            if (noteSuccess_isPresent) {
                System.out.println("Image Successfully uploaded. \n---------------");
            } else {
                Assert.fail("Success note is not displayed.");
            }

        }
    }
}
