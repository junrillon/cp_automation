package engine;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class GoogleChromeDriver extends ChromeDriver {


    GoogleChromeDriver() {
        super(getCaps());
    }

    private static ChromeOptions getCaps() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--no-sandbox"); // Required for running as root user
        options.addArguments("--disable-dev-shm-usage"); // Avoids issues with /dev/shm size
        options.addArguments("-foreground");
        options.addArguments("start-maximized");
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability("ignoreZoomSetting", true);
        options.setCapability("requireWindowFocus", false);
        options.setCapability("enablePersistentHover", false);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, getExecutable());
        return options;
    }

    private static String getExecutable() {
        String osName = System.getProperty("os.name").toLowerCase();
        String executablePath;

        if (osName.contains("win")) {
            executablePath = "executable/win/chromedriver.exe";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            executablePath = "executable/linux/chromedriver";
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(executablePath);
        File exe = null;
        try {
            exe = File.createTempFile("chromedriver", osName.contains("win") ? ".exe" : "");
            FileUtils.copyInputStreamToFile(is, exe);

            // Set executable permission
            exe.setExecutable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(exe).getAbsolutePath();
    }
}