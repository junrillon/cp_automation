package pages.aams.settings.pages;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pages {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "content")
    public WebElement content;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/create')]")
    public WebElement createPageButton;

    @FindBy(how = How.ID, using = "keyword")
    public WebElement searchInput;

    @FindBy(how = How.ID, using = "filter_status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "filter_button")
    public WebElement filterButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/edit')]")
    public WebElement editPageButton;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']")
    public WebElement table;

    public Pages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreatePageButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(createPageButton));
            wait.until(ExpectedConditions.elementToBeClickable(createPageButton)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickCreatePageButton();
        }
    }

    public String getCellValue(WebElement tableElement, int index) {
        String cellValue = null;

        // Get all rows from the table
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        // Iterate through the rows (excluding the header row)
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Get all cells in the row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Get the value of the first cell and assign it to variable cellValue
            cellValue = cells.get(index).getText();
        }

        return cellValue;
    }

    public int getCellCount(WebElement tableElement) {
        int cellCount = 0;

        // Get all rows from the table
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        // Iterate through the rows (excluding the header row)
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Get all cells in the row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Get the value of the first cell and assign it to variable cellValue
            cellCount = cells.size();
        }

        return cellCount;
    }

    public static List<String[]> readDataFromCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return data;
    }
}
