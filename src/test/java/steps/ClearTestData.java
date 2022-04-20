package steps;

import database.DatabaseConnection;
import engine.Driver;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClearTestData {

    private WebDriver driver;

    public ClearTestData(Driver driver) {
        this.driver = driver.get();
    }

    String pmatchValue;
    @Given("I clear the pool match data")
    public void iClearTheMatchTestDataOnPool() throws SQLException {

        //CLear p match and selection via SP
       String getMatchId = "CALL DBAdmin.sp_clearMatch_Automate();";
       DatabaseConnection.executeDBUpdate(getMatchId);


        try{

            //check p match if null
            String p_match = "SELECT id FROM `stage_pool_betting`.`p_match` WHERE sport_id = 83 AND league_id = 191";
            ResultSet p_match_bet_selection = DatabaseConnection.execDBQuery(p_match);
            pmatchValue = p_match_bet_selection.getString("id");


        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("match value " + pmatchValue);

        // assert match id should be empty
        Assert.assertEquals(null, pmatchValue);

    }
}
