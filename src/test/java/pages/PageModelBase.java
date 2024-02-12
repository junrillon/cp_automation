package pages;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Tools;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static utilities.Tools.logger;

public class PageModelBase {
    private final WebDriver driver;

    public PageModelBase(WebDriver driver) {
        this.driver = driver;

    }

    /**
     * @param element to scroll into view
     * @param  */
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        int elementPositionY = element.getLocation().getY();
        int windowHeight = driver.manage().window().getSize().getHeight();
        int scrollOffset = windowHeight / 2;

        js.executeScript("window.scrollBy(0, arguments[0] - arguments[1]);", elementPositionY, scrollOffset);
    }

    public void scrollIntoView1(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isElementDisplayed = (boolean) js.executeScript(
                "var elem = arguments[0];" +
                        "var rect = elem.getBoundingClientRect();" +
                        "var viewHeight = Math.max(document.documentElement.clientHeight, window.innerHeight);" +
                        "var isElementDisplayed = !(rect.bottom < 0 || rect.top - viewHeight >= 0);" +
                        "return isElementDisplayed;", element);

        if (!isElementDisplayed) {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        }
    }

    /** Scroll to the top of webpage */
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    /** seconds to sleep thread -> ONLY USE THIS WHEN ABSOLUTELY NECESSARY. KEEP AS PRIVATE!!! */
    private void sleep(int seconds) {
        logger().traceEntry();

        int sec = seconds * 1000;
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger().traceExit();
    }

    /** seconds to sleep thread -> ONLY USE THIS WHEN ABSOLUTELY NECESSARY. KEEP AS PRIVATE!!! */
    private void sleep(long milliseconds) {
        logger().traceEntry();

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger().traceExit();
    }

    /**
     * @param element attempts to click on element normally but if it fails we click on it with
     *     JavascriptExecutor
     */
    public void click(WebElement element) {
        Tools.logger().traceEntry();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
        Tools.logger().traceExit();
    }

    /**
     * @param elems list of elements to click on
     * @param index index to start clicking from
     * @param amount how many times to click on elements Sequentially
     */
    public void clickSequentially(List<WebElement> elems, Integer index, Integer amount) {
        logger().traceEntry();

        int clicked = 0;

        while (clicked < amount) {
            elems.get(index).click();
            clicked++;
            index++;
        }

        logger().traceExit();
    }

    /**
     * @param element element that we want to click on
     * @param amount how many times to click on element
     */
    public void clickMultiple(WebElement element, Integer amount) {
        logger().traceEntry();

        int clicked = 0;

        while (clicked < amount) {
            element.click();
            clicked++;
        }

        logger().traceExit();
    }

    /**
     * waits for an element to become stale for 1 second. If it does not become stale it throws the
     * exception and continues.
     *
     * @param element to wait for staleness of
     */
    public void waitForNotStale(WebElement element, int seconds) {
        logger().traceEntry();
        fluentWait(seconds, 1).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        sleep((long) 500);
        logger().traceExit();
    }

    public void retryFindElements(List<WebElement> elements, int retryCount, int seconds, int index) throws InterruptedException {
        retryCount = 0;
        while (retryCount < 3) {
            System.out.println("retryCount: " + retryCount);
            try {
                // Re-find the cards element to refresh the reference
                fluentWait(seconds, 1).until(ExpectedConditions.visibilityOf(elements.get(index)));

                break; // Break the loop if element is found without a stale exception
            } catch (StaleElementReferenceException e) {
                // Wait for a short duration before retrying
                Thread.sleep(1000);

                retryCount++;
            }
        }
    }

    public void waitForVisibility(WebElement element, int seconds) {
        logger().traceEntry();

        fluentWait(seconds, 1).until(ExpectedConditions.visibilityOf(element));

        logger().traceExit();
    }

    public FluentWait<WebDriver> fluentWait(Integer seconds, Integer pollTime) {
        logger().traceEntry();

        assertWaitLimit(seconds);

        FluentWait<WebDriver> fluentWait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(seconds))
                        .pollingEvery(Duration.ofSeconds(pollTime))
                        .ignoring(AssertionError.class)
                        .ignoring(IndexOutOfBoundsException.class)
                        .ignoring(WebDriverException.class);

        if (seconds == 180)
            fluentWait.withMessage(
                    "Time waited reached [3 minute] mark. Test was failed for taking too long.");

        logger()
                .info(
                        String.format(
                                "Waiting:[%ss] and pollingEvery:[%ss] for condition to be met", seconds, pollTime));

        logger().traceExit(fluentWait);
        return fluentWait;
    }

    private void assertWaitLimit(int seconds) {
        boolean timeToWaitIsLessThan3min = 0 < seconds && seconds < 181;

        if (!timeToWaitIsLessThan3min) {
            Assert.fail("Time waited needs to be greater than 0 and less than 3 minutes");
        }
    }

    public void waitForModalToClose(WebElement modal, int timeoutInSeconds) {
        boolean isOpen;
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000L;
        do {
            isOpen = true;
            try {
                modal.isDisplayed();
                isOpen = false;
            } catch (NoSuchElementException e) {
                // The modal is no longer present in the DOM, so it is closed
            }
        } while (!isOpen && System.currentTimeMillis() < endTime);

        if (!isOpen) {
            throw new TimeoutException("Modal did not close within the specified timeout period.");
        }
    }

    public boolean waitForModalToOpen(WebElement modal, int timeoutInSeconds) {
        boolean isOpen;
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000L;

        do {
            isOpen = false;
            try {
                modal.isDisplayed();
                isOpen = true;
            } catch (NoSuchElementException e) {
                // The modal is no longer present in the DOM, so it is closed
            }
        } while (!isOpen && System.currentTimeMillis() < endTime);

        if (!isOpen) {
            throw new TimeoutException("Modal did not close within the specified timeout period.");
        }

        return true;
    }

    // Clear the input field
    public void clearInputField(WebElement inputField){
        inputField.clear();

    }

    public void enterValue(WebElement element, String value){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Clear the input field
            clearInputField(element);

            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(value);

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            enterValue(element, value);
        }

    }

    public void selectDropdownOption(WebElement element, String optionValue){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.visibilityOf(element)).click();

            Select select = new Select(element);

            // Iterate over the options and select the matching option (case-insensitive)
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().toLowerCase().contains(optionValue.toLowerCase())) {
                    option.click();
                    break;
                }
            }

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            selectDropdownOption(element, optionValue);
        }
    }

    public void deselectDropdownOption(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible
            wait.until(ExpectedConditions.visibilityOf(element));

            // Create a Select object
            Select select = new Select(element);

            // Check if the dropdown is a multi-select
            boolean isMultiSelect = select.isMultiple();

            // Clear the selection based on the dropdown type
            if (isMultiSelect) {
                select.deselectAll();
            } else {
                select.selectByIndex(0);
            }

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());
        }
    }

    public void clickButton(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element));

            //JavascriptExecutor executor = (JavascriptExecutor) driver;
            //executor.executeScript("arguments[0].click();", element);

            wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickButton(element);
        }
    }

    public  void performClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element)
                .moveByOffset(0, 0)
                .click()
                .perform();
    }
    public List<String[]> readDataFromCSV(String filePath) {
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

    public List<String> retrieveDataFromWebsiteTable(WebElement tableElement) {

        // Find all the rows in the table
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        // Create an ArrayList to store the table data
        List<String> tableData = new ArrayList<>();

        // Iterate over the rows and extract the cell values
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                StringBuilder rowData = new StringBuilder();
                for (WebElement cell : cells) {
                    String cellText = cell.getText();
                    rowData.append(cellText).append(", ");
                }
                // Remove the trailing ", " from the last cell
                if (rowData.length() > 0) {
                    rowData.setLength(rowData.length() - 2);
                }
                tableData.add(rowData.toString());
            }
        }

        return tableData;
    }

    public List<String> getDataFromExcelFile(String filePath) {
        List<String> excelData = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#");

        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows starting from the second row (index 1)
            for (int rowIndex = 1; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                StringBuilder rowData = new StringBuilder();

                // Iterate over cells
                for (Cell cell : row) {
                    // Get the cell value based on its data type
                    CellType cellType = cell.getCellType();

                    if (cellType == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        rowData.append(cellValue).append(", ");

                    } else if (cellType == CellType.NUMERIC) {
                        double cellValue = cell.getNumericCellValue();
                        String formattedValue = decimalFormat.format(cellValue);
                        rowData.append(formattedValue).append(", ");

                    } else if (cellType == CellType.BOOLEAN) {
                        boolean cellValue = cell.getBooleanCellValue();
                        rowData.append(cellValue).append(", ");

                    } else if (cellType == CellType.BLANK) {
                        rowData.append(", ");
                    }
                }

                // Remove the trailing ", " from the last cell
                if (rowData.length() > 0) {
                    rowData.setLength(rowData.length() - 2);
                }

                excelData.add(rowData.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelData;
    }

    public void assertModalMessage(List<WebElement> modalElements, WebElement modalMessage, WebElement modalCloseButton, String expectedMessage) {
        int maxRetry = 3;

        for (int retry = 0; retry <= maxRetry; retry++) {
            if (modalElements.size() == 1) {
                WebElement visibleModalMessage = new WebDriverWait(driver, 20)
                        .until(ExpectedConditions.visibilityOf(modalMessage));

                String actualStatusMessage = visibleModalMessage.getText();
                System.out.println(actualStatusMessage);

                clickButton(modalCloseButton);

                org.testng.Assert.assertEquals(actualStatusMessage.trim(), expectedMessage,
                        "actual Message: " + actualStatusMessage);

                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchToFrame(WebElement element){
        WebElement frame = new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOf(element));

        driver.switchTo().frame(frame);
    }
}
