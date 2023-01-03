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

    //Navigation - Fund history button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/fund-history']")
    public WebElement FundHistory;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[1]//h4")
    public WebElement balanceDisplay;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[2]//h4")
    public WebElement bonusDisplay;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[3]//h4")
    public WebElement rolloverDisplay;

    //Fund History Table
    @FindBy(how = How.XPATH, using =  ".//div[@class='table-responsive']")
    public WebElement fundHistoryTable;

    //First row (Transaction ID)
    @FindBy(how = How.XPATH, using =  ".//div[@class='table-responsive']//tr[1]//td[@class='hidden-mobile']")
    public WebElement firstRowTransId;





}
