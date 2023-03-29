package steps;

import engine.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Screenshot {




    public static void screenshots(WebDriver driver) throws IOException {



      //  Date currentDate  = new Date();
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
        driver.quit();






    }

}
