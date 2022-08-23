package pages.frontend.brasilbet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class FundHistory {

    public FundHistory(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Fund History Table
    @FindBy(how = How.XPATH, using =  ".//div[@class='table-responsive']")
    public WebElement fundHistoryTable;

    //First row (Transaction ID)
    @FindBy(how = How.XPATH, using =  ".//div[@class='table-responsive']//tr[1]//td[@class='hidden-mobile']")
    public WebElement firstRowTransId;



}
