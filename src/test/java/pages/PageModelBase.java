package pages;

import engine.Driver;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static utilities.Tools.logger;

public class PageModelBase {
    public WebDriver driver;
    public PageModelBase(Driver driver) {
        this.driver = driver.get();

    }

    /** @param element to scroll into view */
    public void scrollIntoView(WebElement element) {
        logger().traceEntry();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        logger().traceExit();
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

}
