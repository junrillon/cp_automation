package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import utilities.Tools;

import java.time.Duration;
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
//        js.executeScript("arguments[0].scrollIntoView(true);", element);
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        js.executeScript(scrollElementIntoMiddle, element);
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

    public void clearInputField(WebElement inputField){
        inputField.clear();

    }

}
