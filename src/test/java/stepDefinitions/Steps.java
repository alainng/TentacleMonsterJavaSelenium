package stepDefinitions;

import applicationPages.HomePage;
import applicationPages.PricesPage;
import applicationPages.DoesntexistPage;
import applicationPages.PgpPage;
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

    @Given("a chrome browser")
    public void createChromeBrowser() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("a firefox browser")
    public void createFirefoxBrowser() {
        System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
        driver=new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @When("user loads the home page")
    public void userLoadsTheHomePage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageOpened());
    }

    @When("user loads the prices page")
    public void userLoadsThePricesPage() {
        PricesPage pricesPage = new PricesPage(driver);
        Assert.assertTrue(pricesPage.isPageOpened());
    }

    @When("user loads the doesntexist page")
    public void userLoadsTheDoesntexistPage() {
        DoesntexistPage doesntexistPage = new DoesntexistPage(driver);
        Assert.assertTrue(doesntexistPage.isPageOpened());
    }

    @When("user loads the pgp page")
    public void userLoadsThePgpPage() {
        PgpPage pgpPage = new PgpPage(driver);
        Assert.assertTrue(pgpPage.isPageOpened());
    }

    @Then("page loads without javascript errors")
    public void pageLoadsWithoutJavascriptErrors() {
        HomePage homePage = new HomePage(driver);
        Assert.assertFalse(homePage.hasJavascriptErrors());
    }

    @When("user clicks on all links")
    public void userClicksOnAllLinks() {
        List<WebElement> listOfWebElements = driver.findElements(By.tagName("a"));
        linksCount = listOfWebElements.size();

        //store list of links before iterating
        listOfLinks= new String[linksCount];
        for(int i=0; i<linksCount; i++) {
            listOfLinks[i] = listOfWebElements.get(i).getAttribute("href");
        }

    }

    @Then("response code is 200")
    public void responseCodeIs200() {
        Assert.assertEquals(200, getResponseCode());
    }

    @Then("all links load to a live page")
    public void allLinksLoadToALivePage() {
        // navigate to each Link on the webpage
        for(int i=0; i<linksCount; i++)
        {
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
//                        System.out.println("---------- bingo !!!!!!!!!!!!!! headers: " + response.get("headers"));
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