package stepDefinitions;

import applicationPages.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Steps {
    public static WebDriver driver;
    private static final String BASE_URL = "https://www.kraken.com/";
    private static String[] listOfLinks = null;
    private static int linksCount = 0;
    private BasePage page;

    @Given("a chrome browser")
    public void createChromeBrowser() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(options);
    }

    @Given("a firefox browser")
    public void createFirefoxBrowser() {
        System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
        driver=new FirefoxDriver();
    }

    @When("user loads the multimodal page")
    public void userLoadsTheMultimodalPage() {
        MultimodalPage multimodalPage = new MultimodalPage(driver);
        Assert.assertTrue(multimodalPage.isPageOpened());
        page = multimodalPage;
    }

    @When("user loads the htmlcss page")
    public void userLoadsTheHtmlcssPage() {
        HtmlcssPage htmlcssPage = new HtmlcssPage(driver);
        Assert.assertTrue(htmlcssPage.isPageOpened());
        page = htmlcssPage;
    }

    @When("user loads the bad page")
    public void userLoadsTheBadPage() {
        page = new BadPage(driver);
    }


    @Then("page loads without javascript errors")
    public void pageLoadsWithoutJavascriptErrors() {
        Assert.assertFalse(page.hasJavascriptErrors());
    }

    @When("user clicks on all links")
    public void userClicksOnAllLinks() {
        List<WebElement> listOfWebElements = driver.findElements(By.tagName("a"));
        linksCount = listOfWebElements.size();

        //store list of links before iterating
        listOfLinks= new String[linksCount];
        for(int i=0; i<linksCount; i++) {
            listOfLinks[i] = listOfWebElements.get(i).getAttribute("href");
            //known bug: string list can contain nulls
        }

    }

    @Then("response code is 200")
    public void responseCodeIs200() {
        Assert.assertEquals(200, getResponseCode());
    }

    @Then("response code is 404")
    public void responseCodeIs404() {
        Assert.assertEquals(404, getResponseCode());
    }

    @Then("all links load to a live page")
    public void allLinksLoadToALivePage() {
        // navigate to each Link on the webpage
        for(int i=0; i<linksCount; i++)
        {
            if(listOfLinks[i] == null){
                continue;
            }
            driver.navigate().to(listOfLinks[i]);
            int status = getResponseCode();
            Assert.assertTrue(status<400 || status>499);
        }

    }

    @Then("the browser is closed")
    public void closeBrowser() {
        driver.quit();
    }

    private int getResponseCode(){
        String currentURL = driver.getCurrentUrl();
        LogEntries logs = driver.manage().logs().get("performance");
        int status = -1;
        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext(); ) {
            LogEntry entry = it.next();
            try {
                JSONObject json = new JSONObject(entry.getMessage());
                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if (method != null && "Network.responseReceived".equals(method)) {
                    JSONObject params = message.getJSONObject("params");

                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");

                    if (currentURL.equals(messageUrl)) {
                        status = response.getInt("status");
                        System.out.println("---------- bingo !!!!!!!!!!!!!! returned response for " + messageUrl + ": " + status);
                        break;
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("\nstatus code: " + status + " currentURL:"+ currentURL);
        return status;
    }
}