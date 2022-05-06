package steps;

import database.DatabaseConnection;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import steps.frontend.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("match value " + pmatchValue);

        // assert match id should be empty
        Assert.assertEquals(null, pmatchValue);

    }


    
    @Given("I clear the casino data")
    public void iClearTheCasinoData(DataTable loginDetails) throws SQLException {
        int count = 0;
        
        //get br_user_id from steps/frontend/Login
        List<List<String>> data = loginDetails.asLists(String.class);

        //Get data table from feature file
        String brUserId = data.get(1).get(0);

        //Clear player's all casino data
        String clearDataSp = "CALL stage_b2c.usp_CasinoDataClean_Automate('"+brUserId+"')";
        System.out.println(clearDataSp);
        DatabaseConnection.execB2CDBQuery(clearDataSp);

        try{
            //check report_casino if null
            String query = "SELECT COUNT(id) AS id FROM report_casino " +
                    "WHERE br_user_id IN (Select id from br_users where username ='"+brUserId+"')";
            ResultSet report_casino = DatabaseConnection.execB2CDBQuery(query);
            count = report_casino.getInt("id");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // assert report_casino if zero (0)
        Assert.assertEquals(0, count);

    }
}
