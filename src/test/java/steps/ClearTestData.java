package steps;

import database.DatabaseConnection;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClearTestData {

    private static WebDriver driver;

    public ClearTestData(Driver driver) {
        this.driver = driver.get();
    }

/*    public static void screenshots(String fileName) throws IOException {

*//*      //  Date currentDate  = new Date();
      //  String screenShotFileName = currentDate.toString().replace(" ","-").replace(":","-");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
       // FileUtils.copyFile(screenshotFile, new File(".//screenshot//"+ fileName + "-" + screenShotFileName+".png"));
        FileUtils.copyFile(screenshot, new File("screenshot.png"));

        FileInputStream fis = new FileInputStream(screenshot);
        byte[] screenshotBytes = new byte[(int)screenshot.length()];
        fis.read(screenshotBytes);
        fis.close();

        // Set up Telegram API endpoint URL and chat ID
        String telegramUrl = "https://api.telegram.org/bot5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg/sendPhoto?chat_id=-1001766036425";

        // Send screenshot to Telegram API as photo
        URL url = new URL(telegramUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String data = "photo=" + new String(screenshotBytes, StandardCharsets.UTF_8);
        connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Screenshot sent to Telegram successfully.");
        } else {
            System.out.println("Failed to send screenshot to Telegram. Response code: " + connection.getResponseMessage());
        }

        connection.disconnect();

        // Close WebDriver
        driver.quit();*//*


}*/



  /*  public static void ICheckTheBrokenLinks() throws IOException {


        try{
        List<WebElement> links=driver.findElements(By.xpath(".//a[contains(@href,'/')]"));

        for(WebElement link : links) {
            String url=link.getAttribute("href");
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            con.connect();

            int respcode = con.getResponseCode();

            if(respcode>=400){
                // System.out.println("11111 " + respcode);

                System.out.println("link text: "+ url +" response code:"+ respcode);

                // driver.quit();
            }




        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/



    String pmatchValue;
   // @Given("I clear the pool match data")
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
