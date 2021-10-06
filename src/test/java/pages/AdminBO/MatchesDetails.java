package pages.AdminBO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MatchesDetails {
    public MatchesDetails(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    /**
     * Object repository
     */
    //Open match button
    @FindBy(how = How.CSS, using =  "button.w-100.btn.btn-success.mb-2.btn-match-open")
    public WebElement openMatch;

    //confirm open
    @FindBy(how = How.CSS, using =  "button.swal2-confirm.swal2-styled")
    public WebElement confirmOpen;



    /**
     * Object action
     */

    //click open match
    public void clickOpenMatch()
    {
        openMatch.isDisplayed();
        openMatch.click();
    }

    //click confirm open
    public void clickConfirmOpen()
    {
        confirmOpen.isDisplayed();
        confirmOpen.click();
    }



}
