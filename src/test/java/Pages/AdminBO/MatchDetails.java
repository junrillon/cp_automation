package Pages.AdminBO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MatchDetails {
    public MatchDetails(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    //matches > match status column
    @FindBy(how = How.XPATH, using = "//td[@id='col-match-status']")
    public WebElement matchStatus;

    public void matchStatusDisplay() {
        matchStatus.isDisplayed();
    }

    //Match Open Button
    @FindBy(how = How.XPATH, using = "//div[@class='col-auto mx-2']/button[@class='w-100 btn btn-success mb-2 btn-match-open']")
    public WebElement matchOpenButton;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']")
    public WebElement openMatchConfirmationModal;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']/div[@class='swal2-actions']/button[contains(text(), 'Yes')]")
    public WebElement confirmOpenMatch;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-popup swal2-modal swal2-icon-success swal2-show']")
    public WebElement openMatchSuccessMessage;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    public WebElement openMatchSuccessModalOkButton;

    //Match Open Button
    public void matchOpenButton(){
        matchOpenButton.isDisplayed();
        matchOpenButton.click();
        openMatchConfirmationModal.isDisplayed();
        confirmOpenMatch.click();
        openMatchSuccessMessage.isDisplayed();
        openMatchSuccessModalOkButton.click();
    }

    //Match Close Button
    @FindBy(how = How.XPATH, using = "//div[@class='col-auto mx-2']/button[@class='w-100 btn btn-danger mb-2 btn-match-close']")
    public WebElement matchCloseButton;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']")
    public WebElement closeMatchConfirmationModal;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-actions']/button[contains(text(), 'Yes')]")
    public WebElement confirmCloseMatch;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-popup swal2-modal swal2-icon-success swal2-show']")
    public WebElement closeMatchSuccessModal;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    public WebElement closeMatchSuccessModalOkButton;

    //Match Close Button
    public void matchCloseButton(){
        matchCloseButton.isDisplayed();
        matchCloseButton.click();
        closeMatchConfirmationModal.isDisplayed();
        confirmCloseMatch.click();
        closeMatchSuccessModal.isDisplayed();
        closeMatchSuccessModalOkButton.click();
    }

    //Match Select Winner
    @FindBy(how = How.XPATH, using = "//button[@id='btn-select-winner']")
    public WebElement matchSelectWinnerButton;

    @FindBy(how = How.XPATH, using = "//div[@id='modal-select-winner']")
    public WebElement matchSelectWinnerModal;

    @FindBy(how = How.XPATH, using = "//div[@id='modal-select-winner']//select[@id='winner']")
    public WebElement matchSelectWinnerDropdown;

    @FindBy(how = How.XPATH, using = "//div[@id='modal-select-winner']//button[@id='btn-match-winner-confirmation']")
    public WebElement matchSelectWinnerSubmitButton;

    @FindBy(how = How.XPATH, using = "//div[@id='modal-match-winner-confirmation']")
    public WebElement matchSelectWinnerModalConfirmation;

    @FindBy(how = How.XPATH, using = "//div[@id='modal-match-winner-confirmation']//button[@id='btn-match-winner-confirmation-submit']")
    public WebElement matchSelectWinnerSubmitButton2;

    @FindBy(how = How.XPATH, using = "//div[@id='swal2-content' and contains(text(),'Successfully saved.')]")
    public WebElement matchSelectWinnerSuccessMessage;

    @FindBy(how = How.XPATH, using = "//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    public WebElement matchSelectWinnerSuccessModalOkButton;

    public void selectMatchWinner(){
        matchSelectWinnerButton.isDisplayed();
        matchSelectWinnerButton.click();
        matchSelectWinnerModal.isDisplayed();
        matchSelectWinnerDropdown.click();
    }

    public void confirmMatchWinner(){
        matchSelectWinnerSubmitButton.isDisplayed();
        matchSelectWinnerSubmitButton.click();
        matchSelectWinnerModalConfirmation.isDisplayed();
        matchSelectWinnerSubmitButton2.click();
        matchSelectWinnerSuccessMessage.isDisplayed();
        matchSelectWinnerSuccessModalOkButton.click();
    }
}

