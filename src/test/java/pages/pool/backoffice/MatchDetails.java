package pages.pool.backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import static steps.pool.backoffice.SettleMatch.winningTeam;

public class MatchDetails {
    public MatchDetails(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    //matches > match details column
    @FindBy(how = How.XPATH, using = ".//table[@class='table table-striped table-hover table-sm pt-2']")
    @CacheLookup
    public WebElement matchDetailsTable;

    //matches > match winner column
    @FindBy(how = How.ID, using = "col-match-winner")
    @CacheLookup
    public WebElement matchWinnerColumn;

    //matches > match settle status column
    @FindBy(how = How.ID, using = "col-match-settlement")
    @CacheLookup
    public WebElement matchSettleStatusColumn;

    //matches > match status column
    @FindBy(how = How.ID, using = "col-match-status")
    @CacheLookup
    public WebElement matchStatusColumn;

    //matches > match winner column
    public void matchWinnerDisplay() {
        matchWinnerColumn.isDisplayed();
    }
    //matches > match settle status column
    public void matchSettleStatusDisplay() {
        matchSettleStatusColumn.isDisplayed();
    }
    //matches > match status column


    //Match Open Button
    @FindBy(how = How.XPATH, using = ".//div[@class='col-auto mx-2']/button[@class='w-100 btn btn-success mb-2 btn-match-open']")
    @CacheLookup
    public WebElement matchOpenButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']")
    @CacheLookup
    public WebElement openMatchConfirmationModal;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']/div[@class='swal2-actions']/button[contains(text(), 'Yes')]")
    @CacheLookup
    public WebElement confirmOpenMatch;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-success swal2-show']")
    @CacheLookup
    public WebElement openMatchSuccessMessage;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    @CacheLookup
    public WebElement openMatchSuccessModalOkButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-error swal2-show']")
    @CacheLookup
    public WebElement modalItself;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-header']//h2[@class='swal2-title']")
    @CacheLookup
    public WebElement modalTitle;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-header']//h2[@class='swal2-title']")
    @CacheLookup
    public WebElement modalContent;

    @FindBy(how = How.ID, using = "col-match-status")
    @CacheLookup
    public WebElement MatchStatusTxt;


    public String GetmatchStatus(){
        String matchStatus = MatchStatusTxt.getText();
        return matchStatus;

    }

    //Match Open Confirmation
//    public void matchOpenConfirmation(){
//        openMatchSuccessMessage.isDisplayed();
//        openMatchSuccessModalOkButton.click();
//    }

    //Match Close Button
    @FindBy(how = How.XPATH, using = ".//div[@class='col-auto mx-2']/button[@class='w-100 btn btn-danger mb-2 btn-match-close']")
    @CacheLookup
    public WebElement matchCloseButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-warning swal2-show']")
    @CacheLookup
    public WebElement closeMatchConfirmationModal;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Yes')]")
    @CacheLookup
    public WebElement confirmCloseMatch;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-popup swal2-modal swal2-icon-success swal2-show']")
    @CacheLookup
    public WebElement closeMatchSuccessModal;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    @CacheLookup
    public WebElement closeMatchSuccessModalOkButton;

    //Match Select Winner
    @FindBy(how = How.ID, using = "btn-select-winner")
    @CacheLookup
    public WebElement matchSelectWinnerButton;

    @FindBy(how = How.ID, using = "modal-select-winner")
    @CacheLookup
    public WebElement matchSelectWinnerModal;

    @FindBy(how = How.ID, using = "winner")
    @CacheLookup
    public WebElement matchSelectWinnerDropdown;

    @FindBy(how = How.ID, using = "btn-match-winner-confirmation")
    @CacheLookup
    public WebElement matchSelectWinnerSubmitButton;

    @FindBy(how = How.ID, using = "modal-match-winner-confirmation")
    @CacheLookup
    public WebElement matchSelectWinnerModalConfirmation;

    @FindBy(how = How.ID, using = "btn-match-winner-confirmation-submit")
    @CacheLookup
    public WebElement matchSelectWinnerSubmitButton2;

    @FindBy(how = How.XPATH, using = ".//div[@id='swal2-content' and contains(text(),'Successfully saved.')]")
    @CacheLookup
    public WebElement matchSelectWinnerSuccessMessage;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    @CacheLookup
    public WebElement matchSelectWinnerSuccessModalOkButton;

//    public static String winningTeams = SettleMatch.winningTeam;
//    @FindBy(how = How.XPATH, using = ".//select[@id='winner']//option[contains(text(),'"+winningTeams+"')]")
//    @CacheLookup
//    public WebElement matchSelectWinner;

    @FindBy(how = How.XPATH, using = ".//button[@id='match-settle-btn']")
    @CacheLookup
    public WebElement settleMatchButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-content']//div[@id='swal2-content' and contains(text(), 'Are you sure you want to settle this match?')]")
    @CacheLookup
    public WebElement settleMatchConfirmationMessage;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Yes')]")
    @CacheLookup
    public WebElement settleMatchConfirmationYesButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-header']//h2[contains(text(), 'Success')]")
    @CacheLookup
    public WebElement settleMatchSuccessMessage;

    @FindBy(how = How.XPATH, using = ".//div[@class='swal2-actions']/button[contains(text(), 'Ok')]")
    @CacheLookup
    public WebElement settleMatchSuccessModalOkButton;

    public String selectWinningTeam = ".//select[@id='winner']//option[contains(text(),'"+winningTeam+"')]";

    //Match Open Button
    public void matchOpenButton(){
        matchOpenButton.isDisplayed();
        matchOpenButton.isEnabled();
        matchOpenButton.click();
        openMatchConfirmationModal.isDisplayed();
        confirmOpenMatch.click();
        openMatchSuccessMessage.isDisplayed();
        openMatchSuccessModalOkButton.click();
    }

    //Match Close Button
    public void matchCloseButton(){
        matchCloseButton.isDisplayed();
        matchCloseButton.isEnabled();
        matchCloseButton.click();
        closeMatchConfirmationModal.isDisplayed();
        confirmCloseMatch.click();
        closeMatchSuccessModal.isDisplayed();
        closeMatchSuccessModalOkButton.click();
    }

    //Select match winner
    public void selectMatchWinner(){
        matchSelectWinnerButton.isDisplayed();
        matchSelectWinnerButton.isEnabled();
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

    public void settleMatchButton(){
        settleMatchButton.isDisplayed();
        settleMatchButton.isEnabled();
        settleMatchButton.click();
        settleMatchConfirmationMessage.isDisplayed();
        settleMatchConfirmationYesButton.click();
        settleMatchSuccessMessage.isDisplayed();
        settleMatchSuccessModalOkButton.click();
    }

}

