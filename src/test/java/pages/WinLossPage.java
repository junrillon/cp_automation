package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WinLossPage {
    public WinLossPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    //Filter date selection
    @FindBy(how = How.ID, using =  "filterSettledDateRange")
    public WebElement Minus1month;
    public void SelectMinus1month(String inputMinus1month)
    {
        Minus1month.clear();
        Minus1month.sendKeys(inputMinus1month);
    }

    //Search button
    @FindBy(how = How.ID, using =  "filterSearchBtn")
    public WebElement SearchBtn;
    public void Search()
    {
        SearchBtn.click();
    }

    //Total stake
    @FindBy(how = How.XPATH, using =  "//td[2]/b")
    public WebElement TotalStake;

    //CM dg
    @FindBy(how = How.XPATH, using =  "//*[@data-crumblabel = 'dg']")
    public WebElement dg;
    public void checkCmdg() { dg.isDisplayed(); }
    public void clickCmdg() { dg.click(); }

    //CM GG
    @FindBy(how = How.XPATH, using =  "//*[@data-crumblabel = 'gg']")
    public WebElement gg;
    public void checkCmgg() { gg.isDisplayed(); }
    public void clickCmgg() { gg.click(); }


    //CM GGp
    @FindBy(how = How.XPATH, using =  "//*[@data-crumblabel = 'ggp']")
    public WebElement ggp;
    public void checkCmggp() { ggp.isDisplayed(); }
    public void clickCmggp() { ggp.click(); }

    //CM hd
    @FindBy(how = How.XPATH, using =  "//*[@data-crumblabel = 'hd']")
    public WebElement hd;
    public void checkCmhd() { hd.isDisplayed(); }
    public void clickCmhd() { hd.click(); }

    //CM ks
    @FindBy(how = How.XPATH, using =  "//*[@data-crumblabel = 'ks']")
    public WebElement ks;
    public void checkCmks() { ks.isDisplayed(); }
    public void clickCmks() { ks.click(); }


    //dg stake
    @FindBy(how = How.XPATH, using =  "(.//*[normalize-space(text()) and normalize-space(.)='dg (D Gross Comm)'])[1]/following::td[1]")
    public WebElement dgStake;
    public String dgStake() { String dgDisplayStaked = dgStake.getText(); return dgDisplayStaked; }

    //hd stake
    @FindBy(how = How.XPATH, using =  "(.//*[normalize-space(text()) and normalize-space(.)='hd (HD)'])[1]/following::td[1]")
    public WebElement hdStake;
    public String hdStake() { String hdDisplayStaked = hdStake.getText(); return hdDisplayStaked; }

    //ks stake
    @FindBy(how = How.XPATH, using =  "(.//*[normalize-space(text()) and normalize-space(.)='ks'])[1]/following::td[1]")
    public WebElement ksStake;
    public String ksStake() { String ksDisplayStaked = ksStake.getText(); return ksDisplayStaked; }





    public String TotalStake() { String agentTotalStake = TotalStake.getText();

        return agentTotalStake;


    }



    }



