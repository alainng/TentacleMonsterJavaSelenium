package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;

import java.util.Date;
import java.util.logging.Level;

public class BasePage {
    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean hasJavascriptErrors(){
        return checkJavaScriptErrors(Level.SEVERE);
    }

    private boolean checkJavaScriptErrors(Level level) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        boolean result = false;
        for (LogEntry entry : logEntries) {
            if (entry.getLevel().equals(level)) {
                System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
                result=true;
            }
        }
        return result;
    }
}
