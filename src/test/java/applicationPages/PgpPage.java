package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PgpPage extends BasePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL = "https://www.kraken.com/en-us/features/security/pgp";

    @FindBy(how = How.LINK_TEXT, using = "Kraken")
    private WebElement heading;

    public PgpPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }


    public boolean isPageOpened() {
        return heading.getText().toString().contains("Kraken");
    }
}
