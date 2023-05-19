package steps;

import engine.Driver;
import org.openqa.selenium.WebDriver;
import pages.frontend.NavigationPage;

public class AgentDashboard {
    private final WebDriver driver;
    private final NavigationPage navigationPage;

    public AgentDashboard(Driver driver) {
        this.driver = driver.get();
        this.navigationPage = new NavigationPage(this.driver);
    }
}
